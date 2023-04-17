package com.mree.demo.mavidev.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;


@Configuration
public class RabbitmqConfig {


    public static final String EMAIL_QUEUE = "email-queue";

    private static final String EMAIL_ROUTING = "email-routing";

    private static final String EMAIL_EXCHANGE = "email-exchange";


    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Queue queue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EMAIL_EXCHANGE);

    }

    @Bean
    public Binding binding(final Queue queue, final DirectExchange directExchange) { //constructor Injection
        return BindingBuilder.bind(queue).to(directExchange).with(EMAIL_ROUTING);
    }
}
