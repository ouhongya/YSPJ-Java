package com.fh.config;

import com.fh.dao.DaoSupport;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.fh.config.DelayedRabbitMQConfig.DELAYED_QUEUE_NAME;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;


@Service
public class DeadLetterQueueConsumer {


    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "delayMessageSender")
    private DelayMessageSender sender;


    private SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");

    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String str = new String(message.getBody());
        System.out.println(str + "--------------------------------/来了");
        //再次发送
        //int time = new Long(System.currentTimeMillis()).intValue();
        //sender.sendDelayMsg("内容", time);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
