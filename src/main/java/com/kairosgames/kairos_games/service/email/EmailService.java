package com.kairosgames.kairos_games.service.email;


public interface EmailService {

    public void sendPasswordResetEmail(String to, String id) ;
}
