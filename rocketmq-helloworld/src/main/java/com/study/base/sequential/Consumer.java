package com.study.base.sequential;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author rth
 * @description: 顺序消息 - 消费者
 * @date 2023/4/28
 */
@Slf4j
public class Consumer {

    public static void main(String[] args) throws Exception{
        // 创建消费者，并指定消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sequential_group1");
        // 指定nameServer地址
        consumer.setNamesrvAddr("192.168.66.101:9876");
        // 订阅topic和tag
        consumer.subscribe("sequential", "tag1");
        // 设置回调函数
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt messageExt : list) {
                    log.info("{}, 消费消息：{}", Thread.currentThread().getName(),new String(messageExt.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();

        log.info("消费者启动成功！");
    }

}
