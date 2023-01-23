package com.todo.app.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

//    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(Object response){
        LOGGER.info(String.format("Received message -> %s", response));
    }
}