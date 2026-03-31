package com.hotelbooking.bookingservice;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue bookingQueue() {
        return new Queue("booking-notifications", false);
    }

    public void sendBookingNotification(String message) {
        rabbitTemplate.convertAndSend("booking-notifications", message);
    }
}