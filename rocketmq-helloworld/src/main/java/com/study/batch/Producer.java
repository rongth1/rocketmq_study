package com.study.batch;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rth
 * @description: 批量发送消息，需要注意一次发送大小不能超过4M
 * @date 2023/4/28
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{
        // 创建Producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_1");
        // 指定nameserver地址
        producer.setNamesrvAddr("192.168.66.101:9876");
        // 启动producer
        producer.start();
        // 创建消息对象，指定topic、tag、消息体
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // 参数1：主题，参数2：消息TAG，参数3： 消息体
            Message message = new Message("batch", "tag1", ("hello world " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            messageList.add(message);
        }
        SendResult sendResult = producer.send(messageList);
        log.info("发送成功： {}", sendResult);
        // 关闭producer
        producer.shutdown();
    }
}
