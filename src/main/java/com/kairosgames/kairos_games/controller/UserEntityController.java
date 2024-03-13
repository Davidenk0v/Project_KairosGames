package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.repository.UserRepository;
import com.kairosgames.kairos_games.service.UserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@RestController
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<UserEntity> findByUserName(@PathVariable String username){
        return ResponseEntity.ok(this.service.findByUsername(username));
    }

    @PostMapping("/users/games/add/{user_id}/{game_id}")
    public ResponseEntity addGameToList(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.addGameToList(user_id, game_id);
        
        return ResponseEntity.ok("Añadido");
    }

    @PostMapping("/users/games/remove/{user_id}/{game_id}")
    public ResponseEntity removeGameToList(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.removeGameToList(user_id, game_id);
        
        return ResponseEntity.ok("Borrado");
    }
    
    
    





    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@RequestParam String id){
        this.service.deleteById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User eliminated");
    }
} 


