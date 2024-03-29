package com.kairosgames.kairos_games.service.auth;

import com.kairosgames.kairos_games.model.ERole;
import com.kairosgames.kairos_games.model.PermissionEntity;
import com.kairosgames.kairos_games.model.RoleEntity;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.auth.AuthResponse;
import com.kairosgames.kairos_games.model.auth.LoginRequest;
import com.kairosgames.kairos_games.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;


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
            java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider()
                );
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
        PermissionEntity readPermission = PermissionEntity.builder()
			.name("READ")
			.build();
        RoleEntity roleUser = RoleEntity.builder()
			.roleEnum(ERole.USER)
			.permissionEntitySet(Set.of(readPermission))
			.build();


        try{
            java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider()
                );
            Optional<UserEntity> optional = userRepository.findByUsername(request.getUsername());
            if(optional.isPresent()){
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