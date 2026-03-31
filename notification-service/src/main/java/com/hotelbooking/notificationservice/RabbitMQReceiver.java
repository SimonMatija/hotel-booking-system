package com.hotelbooking.notificationservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {

    @RabbitListener(queues = "booking-notifications")
    public void receiveMessage(String message) {
        System.out.println("Notification received: " + message);
    }
}