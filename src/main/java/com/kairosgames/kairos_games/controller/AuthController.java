package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.model.ERole;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.auth.AuthResponse;
import com.kairosgames.kairos_games.model.auth.LoginRequest;
import com.kairosgames.kairos_games.model.auth.RegisterRequest;
import com.kairosgames.kairos_games.service.UserDetailService;
import com.kairosgames.kairos_games.service.UserDetailsServiceImpl;
import com.kairosgames.kairos_games.service.auth.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final AuthService authService;
    private final UserDetailsServiceImpl service;
    @Autowired

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> registrer(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

}
