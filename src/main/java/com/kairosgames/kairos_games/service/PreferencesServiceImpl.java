package com.kairosgames.kairos_games.service;

import com.kairosgames.kairos_games.model.Preferences;
import com.kairosgames.kairos_games.repository.PreferencesRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferencesServiceImpl {

    private PreferencesRepository repository;

    public PreferencesServiceImpl(PreferencesRepository repository){
        this.repository = repository;
    }

    public List<Preferences> findAll(){return this.repository.findAll();}

    public Optional<Preferences> findById(@NonNull Long id){
        return this.repository.findById(id);
    }

    public Preferences save (@NonNull Preferences preferences){
        this.repository.save(preferences);
        return preferences;
    }

    public void deleteById(@NonNull Long id){
        this.repository.deleteById(id);
    }

    public void deleteAll(){
        this.repository.deleteAll();
    }
}
