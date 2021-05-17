package com.evoke.jms.controller;

import java.util.concurrent.TimeUnit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.evoke.jms.service.Receiver;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    @Value("${jms.topic.name}")
    private String topicExchangeName;

    @Value("${jms.queue.name}")
    private String queueName;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, "", "Haii this is Ram!");
        receiver.getLatch()
                .await(10000, TimeUnit.MILLISECONDS);
    }

}
