package com.todo.app.producer;

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

    private static final Logger logger = LogManager.getLogger(TaskService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Sends the Response Object to the Queue which then can be used by the Consumer
    public void sendMessage(Object response) {
        logger.info(String.format("Message sent -> %s", response.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, response);
    }

}