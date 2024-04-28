package com.kairosgames.kairos_games.service;

import com.kairosgames.kairos_games.model.Preferences;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.UserPreferenceRequest;
import com.kairosgames.kairos_games.model.UserPreferences;
import com.kairosgames.kairos_games.repository.UserPreferenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPreferenceService {
    private UserPreferenceRepository userPreferenceRepository;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PreferencesServiceImpl preferencesService;

    @Transactional
    public void saveAllPreferences(Long userId, List<UserPreferenceRequest> preferences) {
        UserEntity user = findUser(userId);
        List<UserPreferences> userPreferences = getUserPreferences(preferences, user);
        userPreferenceRepository.saveAll(userPreferences);
    }

    private List<UserPreferences> getUserPreferences(List<UserPreferenceRequest> preferences, UserEntity user) {
        List<UserPreferences> userPreferences = new ArrayList<>();
        for (UserPreferenceRequest pref : preferences){
            Preferences preference = findThePreference(pref);
            UserPreferences userPreference = new UserPreferences();
            userPreference.setUserId(user);
            userPreference.setPreferenceId(preference);
            userPreference.setResponse(pref.getResponse());
            userPreferences.add(userPreference);
        }
        return userPreferences;
    }

    private Preferences findThePreference(UserPreferenceRequest pref) {
        return preferencesService.findById(pref.getPreferenceId())
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada"));
    }

    private UserEntity findUser(Long userId) {
        return userDetailService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
