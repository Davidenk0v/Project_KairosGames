package com.kairosgames.kairos_games.service.auth;

import com.kairosgames.kairos_games.model.ERole;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.auth.AuthResponse;
import com.kairosgames.kairos_games.model.auth.LoginRequest;
import com.kairosgames.kairos_games.model.auth.RegisterRequest;
import com.kairosgames.kairos_games.repository.UserRepository;


import lombok.RequiredArgsConstructor;

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
    private UserEntity userEntity;
    private final JwtService jwtService;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        String userName = request.getUsername();
        userEntity = userRepository.findByUsername(userName).get();
        return new AuthResponse(token);
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
                .token(jwtService.getToken(user))

                .build();
        }catch(Exception e){
            throw new Exception(e);
        }
    }

}
