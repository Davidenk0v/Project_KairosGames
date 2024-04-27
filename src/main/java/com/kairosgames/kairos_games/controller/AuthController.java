package com.kairosgames.kairos_games.controller;

import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.auth.LoginRequest;
import com.kairosgames.kairos_games.service.auth.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception{
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrer(@RequestBody UserEntity user) throws Exception {
        return authService.register(user);
    }

}
