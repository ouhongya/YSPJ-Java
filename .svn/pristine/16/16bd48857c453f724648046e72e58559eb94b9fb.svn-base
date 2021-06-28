package com.fh.config;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqPublish {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送普通消息
     * @param routingKey
     * @param content
     */
    public void sendMsg(String routingKey, String content){
        rabbitTemplate.convertAndSend(RabbitConfig.Default_Exchange_Name , routingKey , content);
    }

    /**
     * 发送延时信息
     * @param content 内容
     * @param routingKey   routingKey
     * @param delay   延时时间，秒
     */
    public void sendTimeoutMsg(String content , String routingKey, int delay){
        // 通过广播模式发布延时消息 延时30分钟 持久化消息 消费后销毁 这里无需指定路由，会广播至每个绑定此交换机的队列
        rabbitTemplate.convertAndSend(RabbitConfig.Delay_Exchange_Name, routingKey, content, message ->{
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setDelay(delay);
            return message;
        });
    }
}