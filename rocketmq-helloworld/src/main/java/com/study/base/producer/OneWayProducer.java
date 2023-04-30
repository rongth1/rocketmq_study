package com.study.base.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author rth
 * @description: 单向发送
 *   生产者并不关心消息发送结果是否成功
 * @date 2023/4/27
 */
@Slf4j
public class OneWayProducer {

    public static void main(String[] args) throws Exception{
        // 创建Producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_3");
        // 指定nameserver地址
        producer.setNamesrvAddr("192.168.66.101:9876");
        // 启动producer
        producer.start();
        // 创建消息对象，指定topic、tag、消息体
        for (int i = 0; i < 100; i++) {
            // 参数1：主题，参数2：消息TAG，参数3： 消息体
            Message message = new Message("base_one_way", "tag2", ("hello world " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送消息
            producer.sendOneway(message);
            Thread.sleep(2 * 1000);
        }
        // 关闭producer
        producer.shutdown();
    }
}
