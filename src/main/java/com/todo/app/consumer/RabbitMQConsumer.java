package com.todo.app.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private static final Logger logger = LogManager.getLogger(RabbitMQConsumer.class);

//    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(Object response){
        logger.info(String.format("Received message -> %s", response));
    }
}