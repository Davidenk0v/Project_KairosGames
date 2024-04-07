package com.kairosgames.kairos_games.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender emailSender;
    private String url = "http://localhost:5173/password/newpassword";

    public void sendPasswordResetEmail(String to, String id) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("noreply@kairosgames.com");
            helper.setTo(to);
            helper.setSubject("Recuperación de contraseña");
            String htmlContent = "<p>Por favor, haga clic en el siguiente enlace para restablecer su contraseña:</p>" +
                    "<a href='" + url + "/" + id + "'>Restablecer contraseña</a>";
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
