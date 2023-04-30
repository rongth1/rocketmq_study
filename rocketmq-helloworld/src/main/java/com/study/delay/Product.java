package com.study.delay;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author rth
 * @description: 延时消息
 * @date 2023/4/27
 */
@Slf4j
public class Product {

    public static void main(String[] args) throws Exception{
        // 创建Producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_1");
        // 指定nameserver地址
        producer.setNamesrvAddr("192.168.66.101:9876");
        // 启动producer
        producer.start();
        // 创建消息对象，指定topic、tag、消息体
        for (int i = 0; i < 10; i++) {
            // 参数1：主题，参数2：消息TAG，参数3： 消息体
            Message message = new Message("delay", "tag1", ("hello world " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 设定延时级别
            message.setDelayTimeLevel(4);
            // 发送消息
            SendResult sendResult = producer.send(message);
            log.info("发送结果：{}", sendResult);
//            Thread.sleep(2 * 1000);
        }
        // 关闭producer
        producer.shutdown();
    }
}
