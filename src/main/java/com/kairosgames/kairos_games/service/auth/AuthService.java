package com.kairosgames.kairos_games.service.auth;

import com.kairosgames.kairos_games.model.ERole;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.auth.AuthResponse;
import com.kairosgames.kairos_games.model.auth.LoginRequest;
import com.kairosgames.kairos_games.model.auth.RegisterRequest;
import com.kairosgames.kairos_games.repository.UserRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;


    @Autowired
    private final JwtService jwtService;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;


    public AuthResponse login(LoginRequest request) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.generateJWT(user.getUsername());
            String userName = request.getUsername();
            UserEntity userEntity = userRepository.findByUsername(userName).get();
            String rol = userEntity.getRol().name();
            Long id = userEntity.getId();
            return new AuthResponse(token, rol, id);
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    public AuthResponse register(RegisterRequest request) throws Exception{
        try{
            UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .edad(request.getEdad())
                .rol(ERole.USER)
                .build();
            
                userRepository.save(user);
            
            return AuthResponse.builder()
                .token(jwtService.generateJWT(user.getUsername()))
                .build();
        }catch(Exception e){
            throw new Exception(e);
        }
    }

}
