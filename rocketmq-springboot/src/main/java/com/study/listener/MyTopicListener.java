package com.study.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author rth
 * @description: TODO
 * @date 2023/4/26
 */
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}", topic = "springboot-rocketmq")
public class MyTopicListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("处理消息：" + s);
    }
}
