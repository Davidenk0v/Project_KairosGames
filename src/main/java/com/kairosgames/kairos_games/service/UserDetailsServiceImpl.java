package com.kairosgames.kairos_games.service;

import com.kairosgames.kairos_games.exceptions.GameBadRequestException;
import com.kairosgames.kairos_games.exceptions.GameNotFoundException;
import com.kairosgames.kairos_games.exceptions.InternalServerErrorException;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.Preferences;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.repository.GameRepository;
import com.kairosgames.kairos_games.repository.PreferencesRepository;
import com.kairosgames.kairos_games.repository.UserRepository;

import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailService {

    
    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PreferencesRepository preferenceRepository;
    
    @Override
    public void addGameToList(@NonNull Long user_id, @NonNull Long game_id) {
        try{
            Game game = this.gameRepository.findById(game_id).get();
            UserEntity user = this.userRepository.findById(user_id).get();
            user.setUser_games(game);
        }catch(Exception e){
            throw new InternalServerErrorException("Error when creating the relationship ");
        }
    }

    public void removeGameToList(@NonNull Long user_id, @NonNull Long game_id) {
        try{
            Game game = this.gameRepository.findById(game_id).get();
            UserEntity user = this.userRepository.findById(user_id).get();
            user.getUser_games().remove(game);
        }catch(Exception e){
            throw new InternalServerErrorException("Error when creating the relationship ");
        }
    }
    
    
    @Override
    public void addPreferenceToUser(@NonNull Long user_id,@NonNull Long preference_id) {
        try{
            Preferences preference = this.preferenceRepository.findById(preference_id).get();
            UserEntity user = this.userRepository.findById(user_id).get();
            user.setPreferences(preference);
        }catch(Exception e){
            throw new InternalServerErrorException("Error when creating the relationship ");
        }
        
    }

    @Override
    public void removePreferenceToUser(@NonNull Long user_id,@NonNull  Long preference_id) {
        try{
            Preferences preference = this.preferenceRepository.findById(preference_id).get();
            UserEntity user = this.userRepository.findById(user_id).get();
            user.getPreferences().remove(preference);
        }catch(Exception e){
            throw new InternalServerErrorException("Error when creating the relationship ");
        }
        
    }

    @Override
    public void deleteAll() {
        this.userRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id);
        if(this.userRepository.findById(id).isEmpty()){
            throw new GameBadRequestException("Requested User with id "+ id +" does not exist");
        }
        
    }

    @Override
    public List<UserEntity> findAll() {
        if(this.userRepository.findAll().isEmpty()){
            throw new GameNotFoundException("No user found in database");
        }
        return this.userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        Objects.requireNonNull(id);
        if(id <= 0){
            throw new GameBadRequestException("The id must be greater than zero");
        }
            Optional<UserEntity> optional = this.userRepository.findById(id);
            if(!optional.isPresent()){
                throw new GameNotFoundException("Requested User with id "+ id +" does not exist");
            }
            return this.userRepository.findById(id);
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no existe"));

                return userEntity;
    }

    // public UserDetails loadByUsername(String username) {
    //     UserEntity userEntity = userRepository.findByUsername(username)
    //             .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no existe"));

    //     List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    //     userEntity.getRoles()
    //         .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        
    //     userEntity.getRoles().stream()
    //         .flatMap(role -> role.getPermissionEntitySet().stream())
    //         .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

    //     return new User(userEntity.getUsername(),
    //                 userEntity.getPassword(),
    //                 userEntity.isEnabled(),
    //                 userEntity.isAccountNonExpired(),
    //                 userEntity.isCredentialsNonExpired(),
    //                 userEntity.isAccountNonLocked(),
    //                 authorityList
    //                 );
    // }

    @Override
    public UserEntity save(UserEntity game) {
        Objects.requireNonNull(game);
        if(game.getId() != null){
            throw new GameBadRequestException("There is already game with this id");
        }
        this.userRepository.save(game);
        return game;
    }

    @Override
    public UserEntity update(@NonNull Long id,@NonNull  UserEntity game) {
        UserEntity old_user = this.userRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("User does not exist"));
        
            old_user.setEmail(null);
            old_user.setFirstName(null);
            old_user.setLastName(null);
            old_user.setPassword(null);
        return null;
    }
}
