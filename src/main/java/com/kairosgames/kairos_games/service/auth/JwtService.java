package com.kairosgames.kairos_games.service.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.kairosgames.kairos_games.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService implements IJwtService{

//    private static final String SECRET_KEY="kdmvp39q0UNgrPTcUp6g6mlK5sGsYjPmOo6XGQ2GZcUVbOGXY7cBXZMxZw3Zxm";
    @Value("classpath:jwtKeys/private_key.pem")
    private Resource privateKeyResource;

    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;

    @Autowired
    private UserRepository repository;

    //REFACTORIZADOS METODOS DEPRECADOS

    @Override
    public String generateJWT(Long userId) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        PrivateKey privateKey = getPrivateKey(privateKeyResource);

        JWSSigner signer = new RSASSASigner(privateKey);

        Date now = new Date();
        String rol = repository.findById(userId).get().getRol().name();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issuer(rol)
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + 14400000)) //Son 4 horas de expiración en milisegundos
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    @Override
    public JWTClaimsSet parseJWT(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        PublicKey publicKey = getPublicKey(publicKeyResource);

        SignedJWT signedJWT = SignedJWT.parse(jwt);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        if(!signedJWT.verify(verifier)){    //Si entra en este if es que no se ha podido validar con nuestra public key
            throw new JOSEException("Invalid signature");
        }

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        if(claimsSet.getExpirationTime().before(new Date())){ // Si el token es válido comprueba si el token está expirado
            throw new JOSEException("Expired token");
        }

        return claimsSet;
    }


    //Metodo que genera la clave privada recogiendola del directorio dónde la tenemos guardada y transformandola
    private PrivateKey getPrivateKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
    }

    //Metodo que genera la clave pública recogiendola del directorio dónde la tenemos guardada y transformandola
    private PublicKey getPublicKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(new PKCS8EncodedKeySpec(decodedKey));
    }


    //    public String getToken(UserDetails user){
//        return getToken(new HashMap<>(), user);
//    }

    //    private String getToken(Map<String, Object> extraClaims, UserDetails user){
//        UserEntity userEntity = repository.findByUsername(user.getUsername()).get();
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(userEntity.getRol().toString())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
//                .signWith(getKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

//    public String getUsernameFromToken(String token){
//        return getClaim(token, Claims::getSubject);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails){
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    private Claims getAllClaims(String token){
//        return  Jwts
//                .parser()
//                .setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
//        final Claims claims = getAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Date getExpiration(String token){
//        return getClaim(token, Claims::getExpiration);
//    }
//
//    private boolean isTokenExpired(String token){
//        return getExpiration(token).before(new Date());
//    }
}