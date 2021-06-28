package com.fh.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.fh.config.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static com.fh.config.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;

@Component
public class DelayMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 消息发送设置延迟时间
     * @param msg
     * @param delayTime
     */
    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }
}
