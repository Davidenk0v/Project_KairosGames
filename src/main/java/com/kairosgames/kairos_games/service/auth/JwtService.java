package com.kairosgames.kairos_games.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.repository.UserRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtService {

    private static final String SECRET_KEY="kdmvp39q0UNgrPTcUp6g6mlK5sGsYjPmOo6XGQ2GZcUVbOGXY7cBXZMxZw3Zxm";

    @Autowired
    private UserRepository repository;

    public String getToken(UserDetails user){
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user){
        UserEntity userEntity = repository.findByUsername(user.getUsername()).get();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userEntity.getRol().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

    if(claimsSet.getExpirationTime().before(new Date())){
        throw new JOSEException("Expired token");
    }

    return claimsSet;

}

private PrivateKey loadPrivateKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
    String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
        .replace("-----BEGIN RSA PRIVATE KEY-----", "")
        .replace("-----END RSA PRIVATE KEY-----", "")
        .replaceAll("\\s", "");
    byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
}

private PublicKey loadPublicKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
    String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replaceAll("\\s", "");
    byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return keyFactory.generatePublic(new PKCS8EncodedKeySpec(decodedKey));
}

}
