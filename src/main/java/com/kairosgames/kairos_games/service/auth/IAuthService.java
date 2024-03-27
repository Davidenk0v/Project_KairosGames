package com.kairosgames.kairos_games.service.auth;

import com.kairosgames.kairos_games.model.auth.LoginRequest;

import java.util.HashMap;

public interface IAuthService {

    public HashMap<String, String> login(LoginRequest request) throws Exception;
}
