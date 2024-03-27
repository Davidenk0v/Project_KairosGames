package com.kairosgames.kairos_games.service.auth;

import com.kairosgames.kairos_games.model.ERole;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.auth.AuthResponse;
import com.kairosgames.kairos_games.model.auth.LoginRequest;
import com.kairosgames.kairos_games.model.auth.RegisterRequest;
import com.kairosgames.kairos_games.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;


@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    //private final AuthenticationManager authenticationManager;


    //LOGIN
    @Override
    public HashMap<String, String> login(LoginRequest request) throws Exception {
        try{
            HashMap<String, String> jwt = new HashMap<>();
                Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
                if(userEntity.isEmpty()){
                    jwt.put("error", "User not registered!");
                    return jwt;
                }
                //Verificamos la contraseña
                if(verifyPassword(request.getPassword(), userEntity.get().getPassword())){
                    jwt.put("jwt", jwtService.generateJWT(userEntity.get().getId()));
                } else {
                    jwt.put("error", "Authentication failed");
                }
                return jwt;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }


    //Metodo que verifica la contraseña
    private boolean verifyPassword(String enteredPassword, String storedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }

    //REGISTER
    public AuthResponse register(UserEntity request) throws Exception {
        try{
            Optional optional = userRepository.findByUsername(request.getUsername());
            if(!optional.isEmpty()){
                throw new UsernameNotFoundException("User already exists!");
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            UserEntity user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(encoder.encode(request.getPassword()))
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .edad(request.getEdad())
                    .rol(ERole.USER)
                    .build();
            userRepository.save(user);

            return AuthResponse.builder()
                    .token(jwtService.generateJWT(user.getId()))
                    .build();
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }
}