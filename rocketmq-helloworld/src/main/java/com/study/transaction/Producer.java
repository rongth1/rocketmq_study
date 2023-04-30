package com.study.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author rth
 * @description: TODO
 * @date 2023/4/29
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{
        // 创建Producer，并指定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("producer_group_2");
        // 指定nameserver地址
        producer.setNamesrvAddr("192.168.66.101:9876");
        // 配置事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             *  该方法中，执行本地事务
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                String tag = message.getTags();
                if (tag.contains("1")) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if(tag.contains("2")) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             *  该方法，MQ进行消息事务回查
             * @param messageExt
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                log.info("回查消息的TAG：{}", messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        // 启动producer
        producer.start();
        // 创建消息对象，指定topic、tag、消息体
        for (int i = 0; i < 3; i++) {
            // 参数1：主题，参数2：消息TAG，参数3： 消息体
            Message message = new Message("transaction", "tag" + i, ("hello world " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 异步发送消息

            TransactionSendResult sendResult = producer.sendMessageInTransaction(message, null);
            log.info("发送结果：{}", sendResult);
        }
        // 关闭producer
//        producer.shutdown();
    }

}
