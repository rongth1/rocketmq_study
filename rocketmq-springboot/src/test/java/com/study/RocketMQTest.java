package com.study;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author rth
 * @description: 生产者测试类
 * @date 2023/4/26
 */
@SpringBootTest(classes = RocketMQApplication.class)
@RunWith(SpringRunner.class)
public class RocketMQTest {

    @Autowired
    private RocketMQTemplate template;

    @Test
    public void send() throws Exception{
        template.convertAndSend("springboot-rocketmq", "hello  world !");
    }

}
