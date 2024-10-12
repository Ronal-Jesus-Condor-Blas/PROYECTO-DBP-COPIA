package com.proyecto_dbp.user.listener;

import com.proyecto_dbp.user.event.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredListener {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        sendWelcomeEmail(event.getUser().getEmail());
    }

    private void sendWelcomeEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Our Service");
        message.setText("Thank you for registering with us!");
        mailSender.send(message);
    }
}