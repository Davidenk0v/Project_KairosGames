package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.repository.UserRepository;
import com.kairosgames.kairos_games.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/password")

public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/link/{email}")
    public HashMap<String, String> sendLink(@PathVariable String email) throws Exception {
        HashMap<String, String> test = new HashMap<>();
        if (this.userRepository.findByEmail(email).isEmpty()) {
            test.put("message", "No existe ese email");
            test.put("status", "404");
            return test;
        } else {
            emailService.sendSimpleMessage(email, "Recuperar contraseña", "esto sería el link");
            test.put("message", "Enlace enviado a su correo");
            test.put("status", "200");
            return test;
        }

    }


    @PostMapping("/set-newpassword/{id}/{email}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @PathVariable String email) throws Exception {

        return null;
    }
}