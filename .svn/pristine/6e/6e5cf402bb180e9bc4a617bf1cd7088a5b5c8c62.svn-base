package com.fh.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String Delay_Exchange_Name = "examExchange";

    public static final String Default_Exchange_Name = "defaultExchange";

    public static final String Order_Pay_Queue_Name = "order_pay";

    public static final String Timeout_Trade_Queue_Name = "deferredProcessing";

    @Bean
    public Queue orderPayQueue() {
        return new Queue(RabbitConfig.Order_Pay_Queue_Name, true);
    }

    @Bean
    public Queue delayPayQueue() {
        return new Queue(RabbitConfig.Timeout_Trade_Queue_Name, true);
    }


    @Bean
    FanoutExchange delayExchange() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        FanoutExchange topicExchange = new FanoutExchange(RabbitConfig.Delay_Exchange_Name, true, false, args);
        topicExchange.setDelayed(true);
        return topicExchange;
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(RabbitConfig.Default_Exchange_Name, true, false);
    }


    @Bean
    public Binding delayPayBind() {
        return BindingBuilder.bind(delayPayQueue()).to(delayExchange());
    }

    @Bean
    public Binding orderPayBind() {
        return BindingBuilder.bind(orderPayQueue()).to(defaultExchange()).with(RabbitConfig.Order_Pay_Queue_Name);
    }

    @Bean
    Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}