package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserEntityController {


    @Autowired
    private UserRepository userRepository;


}
