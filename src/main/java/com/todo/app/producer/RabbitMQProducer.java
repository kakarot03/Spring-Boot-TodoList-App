package com.todo.app.producer;

import com.todo.app.consumer.Consumer;
import com.todo.app.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.userRouting.key}")
    private String userRoutingKey;

    private static final Logger logger = LogManager.getLogger(TaskService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Object response) {
        logger.info(String.format("Message sent -> %s", response.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, response);
    }

    // Sends the Response Object to the Queue which then can be used by the Consumer
    public void sendMessageToUserQueue(Consumer consumer) {
        logger.info(String.format("Message sent to User Queue -> %s", consumer.toString()));
        rabbitTemplate.convertAndSend(exchange, userRoutingKey, consumer);
    }
}