package com.study.base.sequential;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author rth
 * @description: 顺序消息 - 生产者
 * @date 2023/4/28
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{
        // 创建生产者并指定生产者组
        DefaultMQProducer producer = new DefaultMQProducer("sequential_producer");
        // 配置nameServer地址
        producer.setNamesrvAddr("192.168.66.101:9876");
        // 启动生产者
        producer.start();
        // 创建消息，并发送
        List<OrderStep> orderList = OrderStep.buildOrders();
        for (OrderStep orderStep : orderList) {
            Message message = new Message("sequential", "tag1", orderStep.toString().getBytes());
            Long orderId = orderStep.getOrderId();
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                /**
                 *
                 * @param list  消息队列的集合
                 * @param message  发送的消息
                 * @param o 业务标识参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Long orderId = (Long) o;
                    int index = (int) (orderId % list.size());
                    return list.get(index);
                }
            }, orderId);

            log.info("发送结果： {}", sendResult);
        }
        // 关闭生产者
        producer.shutdown();
    }
}
