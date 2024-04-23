package com.kairosgames.kairos_games.service.email.resendApi;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ResendAPI {
    static Resend resend = new Resend("re_9RKhhBRK_Cr1ARtiNc1BKUGo9WGWTT16x");

    public static SendEmailResponse sendEmail(String email) throws ResendException {
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .from("donotreply@kairosgames.com")
                .to(email)
                .subject("Recuperar tu contraseña")
                .html("<p>Congrats on sending your <strong>first email</strong>!</p>")
                .build();

        SendEmailResponse data = resend.emails().send(sendEmailRequest);
        return data;
    }
}

