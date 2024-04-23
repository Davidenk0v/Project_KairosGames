package com.kairosgames.kairos_games.service.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

public interface IJwtService {

    public String generateJWT(Authentication authentication) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException;

    String extractUsername(JWTClaimsSet jwtClaimsSet);

    String getSpecificClaim(JWTClaimsSet jwtClaimsSet, String claimName);

    public JWTClaimsSet parseJWT(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException;
}
