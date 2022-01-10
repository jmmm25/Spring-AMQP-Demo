package com.example.amqp;

import com.example.amqp.config.AmqpConfig;
import com.example.amqp.domain.Information;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AmqpSender implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;

    public AmqpSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        // topic 방식
        Information topicInformation = new Information();
        topicInformation.setId("jmtest_topic1");
        topicInformation.setName("topic_message");
        topicInformation.setAge(31);
        topicInformation.setAddress("Korea Seoul");
        rabbitTemplate.convertAndSend(AmqpConfig.TOPIC_EXCHANGE_NAME, AmqpConfig.TOPIC_ROUTING_KEY + "TOPIC1", topicInformation);

        Information topicInformation2 = new Information();
        topicInformation2.setId("jmtest_topic2");
        topicInformation2.setName("topic_message");
        topicInformation2.setAge(32);
        topicInformation2.setAddress("Korea Seoul");
        rabbitTemplate.convertAndSend(AmqpConfig.TOPIC_EXCHANGE_NAME, AmqpConfig.TOPIC_ROUTING_KEY + "TOPIC2", topicInformation2);


        // fanout 방식
        Information fanoutInformation = new Information();
        fanoutInformation.setId("jmtest_fanout");
        fanoutInformation.setName("fanout_message");
        fanoutInformation.setAge(33);
        fanoutInformation.setAddress("Korea Seoul");
        rabbitTemplate.convertAndSend(AmqpConfig.FANOUT_EXCHANGE_NAME, "", fanoutInformation);

        // direct 방식
        Information directInformation = new Information();
        directInformation.setId("jmtest_direct");
        directInformation.setName("direct_message");
        directInformation.setAge(34);
        directInformation.setAddress("Korea Seoul");
        rabbitTemplate.convertAndSend(AmqpConfig.DIRECT_EXCHANGE_NAME, AmqpConfig.DIRECT_ROUTING_KEY, directInformation);
    }
}
