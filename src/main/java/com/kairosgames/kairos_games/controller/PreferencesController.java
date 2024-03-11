package com.kairosgames.kairos_games.controller;

import com.kairosgames.kairos_games.service.PreferencesServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreferencesController {

    private PreferencesServiceImpl service;

    public PreferencesController(PreferencesServiceImpl service){
        this.service = service;
    }


}
