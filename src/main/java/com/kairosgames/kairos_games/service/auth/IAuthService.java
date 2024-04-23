package com.kairosgames.kairos_games.service.auth;

import com.kairosgames.kairos_games.model.auth.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

    public ResponseEntity<?> login(LoginRequest request) throws Exception;
}
