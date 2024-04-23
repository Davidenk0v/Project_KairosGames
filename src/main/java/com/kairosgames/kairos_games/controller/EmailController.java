package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.repository.UserRepository;
import com.kairosgames.kairos_games.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/password")

public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/link/{email}")
    public ResponseEntity<?> sendLink(@PathVariable String email) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("No existe ese email", HttpStatus.NOT_FOUND);
        } else {
            UserEntity user = userRepository.findByEmail(email).get();
            emailService.sendPasswordResetEmail(email, user.getId().toString());
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        }

    }

    @PostMapping("/set-newpassword/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody String newPassword) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
           UserEntity user = userOptional.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
           user.setPassword(encoder.encode(newPassword));
           userRepository.save(user);
           return new ResponseEntity<>("Contraseña actualizada correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error al cambiar la contraseña", HttpStatus.BAD_REQUEST);
    }
}