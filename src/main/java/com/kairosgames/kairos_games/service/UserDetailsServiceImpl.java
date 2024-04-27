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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserDetailsServiceImpl implements UserDetailService {

    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PreferencesRepository preferenceRepository;

    @Autowired
    private PasswordEncoder encoder;
    
    @Override
    public void addGameToList(@NonNull Long user_id, @NonNull Long game_id) {
        try{
            Game game = this.gameRepository.findById(game_id).get();
            UserEntity user = this.userRepository.findById(user_id).get();
            user.setUser_games(game);
            userRepository.save(user);
        }catch(Exception e){
            throw new InternalServerErrorException("Error when creating the relationship ");
        }
    }

    @Override
    public List<Preferences> allPreferences() {
        return this.preferenceRepository.findAll();
    }

    public void removeGameToList(@NonNull Long user_id, @NonNull Long game_id) {
        try{
            Game game = this.gameRepository.findById(game_id).get();
            UserEntity user = this.userRepository.findById(user_id).get();
            user.getUser_games().remove(game);
            this.userRepository.save(user);
        }catch(Exception e){
            throw new InternalServerErrorException("Error when removing the relationship ");
        }
    }
    

    @Override
    public void removePreferenceToUser(@NonNull Long user_id,@NonNull  Long preference_id) {
        try{
            Preferences preference = this.preferenceRepository.findById(preference_id).get();
            UserEntity user = this.userRepository.findById(user_id).get();
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
        userRepository.deleteById(id);
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
    public Optional<UserEntity> findByEmail(String email) {

        Optional<UserEntity> user = this.userRepository.findByEmail(email);
                if(user.isEmpty()){
                    throw new UsernameNotFoundException("Requested User with email "+ email +" does not exist");
                }

        return user;
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no existe"));

                return userEntity;
    }

    @Override
    public Set<Game> getUserGames(Long id){
        UserEntity user = this.userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con el id " + id + " no existe"));
        return user.getUser_games();
    }


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
    public UserEntity update(@NonNull Long id, @NonNull  UserEntity newUser) {
        UserEntity old_user = this.userRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("User does not exist"));
        Optional<UserEntity> optional = this.userRepository.findByUsername(newUser.getUsername());

        if(optional.isPresent() && !old_user.getId().equals(optional.get().getId())) {
            throw new UsernameNotFoundException("Ya existe usuario con ese username");
        }

        old_user.setUsername(newUser.getUsername());
        old_user.setEdad(newUser.getEdad());
        old_user.setEmail(newUser.getEmail());
        old_user.setFirstName(newUser.getFirstName());
        old_user.setLastName(newUser.getLastName());
        if(old_user.getPassword() != newUser.getPassword()){
            old_user.setPassword(encoder.encode(newUser.getPassword()));
        }


        userRepository.save(old_user);
        return old_user;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no existe"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();


        userEntity.getRoles()
                .forEach(role-> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat((role.getRol().name())))));

        userEntity.getRoles()
                .stream()
                .flatMap(rolEntity -> rolEntity.getPermissionList().stream())
                .forEach(permissionEntity -> authorityList.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList);
    }

    public Authentication authenticate(String username, String password){

        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!encoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException(("Invalid password"));
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
