package com.kairosgames.kairos_games;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.repository.GameRepository;
import com.kairosgames.kairos_games.repository.UserRepository;

import jakarta.transaction.Transactional;

@DataJpaTest
public class UserGameTest {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GameRepository gameRepository;
    UserEntity usuario = new UserEntity();
    Game juego = new Game();

    @Test
     void testAgregarYObtenerJuego() {
         usuario.setUsername("UserEntity de prueba");
         usuario.setEmail("email@gmail.com");
         usuario.setPassword("1234");
         usuario.setEdad(44);
         userRepository.save(usuario);
        // Crear un juego
        juego.setName("Game de prueba");
        gameRepository.save(juego);

        // Crear un usuario

        // Agregar el juego al usuario
        usuario.getUser_games().add(juego);
        userRepository.save(usuario);

        // Obtener el usuario de la base de datos
        UserEntity usuarioGuardado = userRepository.findById(usuario.getId()).orElse(null);

        // Verificar si el juego se agregó correctamente al usuario
        assertEquals(1, usuarioGuardado.getUser_games().size());
        assertEquals("Game de prueba", usuarioGuardado.getUser_games().iterator().next().getName());
    }
    @Test
    @Transactional
    public void testEliminarRelacionUsuarioJuego() {
        // Crear un usuario y un juego

        usuario.setUsername("Usuario de prueba");
        usuario.setUsername("UserEntity de prueba");
         usuario.setEmail("email@gmail.com");
         usuario.setPassword("1234");
         usuario.setEdad(44);
        usuario = userRepository.save(usuario);

        juego.setName("Game de prueba");
        juego = gameRepository.save(juego);

        // Establecer la relación entre el usuario y el juego
        usuario.setUser_games(juego);
        userRepository.save(usuario);

        // Verificar que la relación se ha establecido correctamente
        assertEquals(1, userRepository.findById(usuario.getId()).get().getUser_games().size());

        // Eliminar la relación entre el usuario y el juego
        usuario.getUser_games().remove(juego);

        // Verificar que la relación se ha eliminado correctamente
        assertEquals(0, userRepository.findById(usuario.getId()).get().getUser_games().size());
    }

}
