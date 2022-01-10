package com.example.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    public static final String TOPIC_QUEUE_NAME = "TOPIC_QUEUE";
    public static final String FANOUT_QUEUE_NAME = "FANOUT_QUEUE";
    public static final String FANOUT_QUEUE2_NAME = "FANOUT_QUEUE2";
    public static final String DIRECT_QUEUE_NAME = "DIRECT_QUEUE";

    public static final String TOPIC_EXCHANGE_NAME = "TOPIC_EXCHANGE";
    public static final String FANOUT_EXCHANGE_NAME = "FANOUT_EXCHANGE";
    public static final String DIRECT_EXCHANGE_NAME = "DIRECT_EXCHANGE";

    public static final String TOPIC_ROUTING_KEY = "TOPIC.ROUTING.";
    public static final String DIRECT_ROUTING_KEY = "DIRECT.ROUTING.KEY";

    @Bean
    public Queue topicQueue() {
        return new Queue(TOPIC_QUEUE_NAME);
    }

    @Bean
    public Queue fanoutQueue() {
        return new Queue(FANOUT_QUEUE_NAME);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2_NAME);
    }

    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE_NAME);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    public Binding topicBinding(Queue topicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with(TOPIC_ROUTING_KEY + "#");
    }

    @Bean
    public Binding fanoutBinding(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    @Bean
    public Binding directBinding(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with(DIRECT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());

        return rabbitTemplate;
    }

}
