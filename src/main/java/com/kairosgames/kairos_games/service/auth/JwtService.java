package com.kairosgames.kairos_games.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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
public class JwtService{

@Value("classpath:jwtKeys/private_key.pem")
private Resource privateKeResource;

@Value("classpath:jwtKeys/public_key.pem")
private Resource publicKeyResource;

public String generateJWT(String username) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException{
    PrivateKey privateKey = loadPrivateKey(privateKeResource);

    JWSSigner signer = new RSASSASigner(privateKey);

    Date now = new Date();
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(username)
        .issueTime(now)
        .expirationTime(new Date(now.getTime() + 14400000))
        .build();
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
    signedJWT.sign(signer);

    return signedJWT.serialize();
}

public JWTClaimsSet parseJWT(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException, ParseException{
    PublicKey publicKey = loadPublicKey(publicKeyResource);

    SignedJWT signedJWT = SignedJWT.parse(jwt);
    JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey)publicKey);

    if(!signedJWT.verify(verifier)){
        throw new JOSEException("Invalid signature");
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
