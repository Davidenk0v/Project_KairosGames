package com.kairosgames.kairos_games.service.email;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;

public class SimpleEmailMessage {

    @Bean
    public SimpleMailMessage templateSimpleMessage(String email, Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "This is the test email template for your email:\n%s\n");
        return message;
    }
}
