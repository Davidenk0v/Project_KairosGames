package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.repository.UserRepository;
import com.kairosgames.kairos_games.service.UserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class UserEntityController {

/*     @Autowired
    private PasswordEncoder passwordEncoder; */

    @Autowired
    private UserDetailService service; 

    /* @PostMapping("/createUser")
    public ResponseEntity<?> createUser(){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .edad(createUserDTO.getEdad())
                .roles(roles)
                .build();
        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    } */
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findById(id).get());
    }

    @GetMapping("/users/name/{name}")
    public ResponseEntity<List<UserEntity>> findByUserName(@PathVariable String username){
        return ResponseEntity.ok(this.service.findByUsername(username));
    }

    @PostMapping("/users/games/add/{user_id}/{game_id}")
    public ResponseEntity addGameToList(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.addGameToPreference(user_id, game_id);
        
        return ResponseEntity.ok("Añadido");
    }
    
    
    





    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@RequestParam String id){
        this.service.deleteById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User eliminated");
    }
} 

}
