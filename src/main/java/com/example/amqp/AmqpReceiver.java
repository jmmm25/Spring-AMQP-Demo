package com.example.amqp;

import com.example.amqp.config.AmqpConfig;
import com.example.amqp.domain.Information;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpReceiver {
    private static Logger logger = LoggerFactory.getLogger(AmqpReceiver.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = AmqpConfig.TOPIC_QUEUE_NAME)
    public void receivedMessage(Message message) throws JsonProcessingException {
        byte[] body = message.getBody();
        logger.info("from topic Queue received message: {}", message);
        logger.info("get String body: {}", new String(body));

        Information information = objectMapper.readValue(new String(body), Information.class);
        logger.info("Information Object : {}", information.toString());
    }

}
