package com.todo.app.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private static final Logger logger = LogManager.getLogger(RabbitMQConsumer.class);
    @Autowired
    private ConsumerRepository consumerRepository;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(Object response){
        logger.info(String.format("Received message -> %s", response));
    }

    // Listens to the User Queue
    @RabbitListener(queues = {"${rabbitmq.userQueue.name}"})
    public void consume(Consumer response){
        logger.info(String.format("Received message from User Queue -> %s", response.toString()));
        consumerRepository.save(response);
    }
}