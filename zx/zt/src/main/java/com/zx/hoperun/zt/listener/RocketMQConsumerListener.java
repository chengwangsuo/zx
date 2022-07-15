package com.zx.hoperun.zt.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "test",topic = "test-topic")
public class RocketMQConsumerListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("消费消息："+s);
    }
}
