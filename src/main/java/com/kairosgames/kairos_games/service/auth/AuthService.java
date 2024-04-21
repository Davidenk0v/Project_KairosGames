package com.kairosgames.kairos_games.service.auth;

import com.kairosgames.kairos_games.exceptions.EmailAlreadyExistException;
import com.kairosgames.kairos_games.model.ERole;
import com.kairosgames.kairos_games.model.RolEntity;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.auth.AuthResponse;
import com.kairosgames.kairos_games.model.auth.LoginRequest;
import com.kairosgames.kairos_games.repository.RoleRepository;
import com.kairosgames.kairos_games.repository.UserRepository;

import com.kairosgames.kairos_games.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDetailsServiceImpl userDetailsServices;

    //LOGIN
    @Override
    public ResponseEntity<?> login(LoginRequest request) throws Exception {
        try{
            Authentication authentication = userDetailsServices.authenticate(request.getUsername(), request.getPassword());
            String jwt = jwtService.generateJWT(authentication);

            Map<String, String> token = new HashMap<>();
            token.put("token", jwt);

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Error:" + e);
            return new ResponseEntity<>("Usuario o contraseña incorrecta", HttpStatus.BAD_REQUEST);
        }
    }


    
    //REGISTER
    public ResponseEntity<?> register(UserEntity request) throws Exception {
        try{
            Optional<UserEntity> optional = userRepository.findByUsername(request.getUsername());
            if(optional.isPresent()){
                throw new UsernameNotFoundException("User already exists!");
            }
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new EmailAlreadyExistException("Email already exists!");
            }
            List<String> rolesRequest = new ArrayList<>();
            rolesRequest.add(ERole.USER.name());
            Set<RolEntity> roleEntityList = roleRepository.findRoleEntitiesByRolIn(rolesRequest).stream().collect(Collectors.toSet());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            UserEntity user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(encoder.encode(request.getPassword()))
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .edad(request.getEdad())
                    .roles(roleEntityList)
                    .build();
            UserEntity userSaved = userRepository.save(user);
            try {

                ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

                userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRol().name()))));

                userSaved.getRoles().stream().flatMap(role -> role.getPermissionList().stream()).forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

                Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

                String jwt = jwtService.generateJWT(authentication);
                Map<String, String> token = new HashMap<>();
                token.put("token", jwt);

                return new ResponseEntity<>(token, HttpStatus.OK);

            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }
}