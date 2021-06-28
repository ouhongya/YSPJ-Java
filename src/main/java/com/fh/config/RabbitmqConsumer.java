package com.fh.config;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitmqConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = RabbitConfig.Timeout_Trade_Queue_Name)
    public void process(String content, Message message, Channel channel) throws IOException {
        try {
            logger.info("延迟队列的内容[{}]", content);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.info("超时信息处理完毕");
        } catch (Exception e) {
            logger.error("处理失败:{}", e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = RabbitConfig.Order_Pay_Queue_Name)
    public void process1(String content, Message message, Channel channel) throws IOException {
        try {
            logger.info("普通队列的内容[{}]", content);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            logger.error("处理失败:{}", e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}