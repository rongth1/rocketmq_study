package com.study.base.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author rth
 * @description: 消费者
 * @date 2023/4/27
 */
@Slf4j
public class Consumer {

    public static void main(String[] args) throws Exception{
        // 创建消费者，指定消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定nameServer地址
        consumer.setNamesrvAddr("192.168.66.101:9876");
        // 订阅topic和tag
        consumer.subscribe("base_async", "tag2");
        // 设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    log.info("消费到的消息：{}", new String(messageExt.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
    }

}
