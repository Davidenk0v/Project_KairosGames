package com.kairosgames.kairos_games;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.repository.GameRepository;
import com.kairosgames.kairos_games.repository.UserRepository;

@DataJpaTest
public class UserGameTest {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GameRepository gameRepository;

    @Test
     void testAgregarYObtenerJuego() {
        // Crear un juego
        Game juego = new Game();
        juego.setName("Game de prueba");
        gameRepository.save(juego);

        // Crear un usuario
        UserEntity usuario = new UserEntity();
        usuario.setUsername("UserEntity de prueba");
        usuario.setEmail("email@gmail.com");
        usuario.setPassword("1234");
        usuario.setEdad(44);
        userRepository.save(usuario);

        // Agregar el juego al usuario
        usuario.getUser_games().add(juego);
        userRepository.save(usuario);

        // Obtener el usuario de la base de datos
        UserEntity usuarioGuardado = userRepository.findById(usuario.getId()).orElse(null);

        // Verificar si el juego se agregó correctamente al usuario
        assertEquals(1, usuarioGuardado.getUser_games().size());
        assertEquals("Game de prueba", usuarioGuardado.getUser_games().iterator().next().getName());
    }
}
