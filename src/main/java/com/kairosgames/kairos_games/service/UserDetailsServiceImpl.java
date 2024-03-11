package com.kairosgames.kairos_games.service;

import com.kairosgames.kairos_games.exceptions.GameBadRequestException;
import com.kairosgames.kairos_games.exceptions.GameNotFoundException;
import com.kairosgames.kairos_games.exceptions.InternalServerErrorException;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.repository.GameRepository;
import com.kairosgames.kairos_games.repository.UserRepository;

import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
/* import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException; */
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailService {

    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;
    
    @Override
    public void addGameToPreference(@NonNull Long user_id, @NonNull Long game_id) {
        try{
            this.userRepository.addGameToUserList(user_id, game_id);
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
    public List<UserEntity> findByUsername(String username) {
        Objects.requireNonNull(username);
        if(this.userRepository.findByUsername(username).isEmpty()){
            throw new GameNotFoundException("Requested User does not exist");
        }
        return this.userRepository.findByUsername(username);
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
    public UserEntity update(Long id, UserEntity game) {
        // TODO Auto-generated method stub
        return null;
    }


/*     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario " +username+" no existe"));
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority("ROLE".concat(role.getName().name())))
                .collect(Collectors.toSet());
        return new User(userEntity.getUsername(), userEntity.getPassword(),true, true , true ,true, authorities);
    } */


}
