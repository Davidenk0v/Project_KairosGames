package com.kairosgames.kairos_games.Jwt;

import com.kairosgames.kairos_games.service.auth.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try{
            JWTClaimsSet claims = jwtService.parseJWT(token);

            String username = jwtService.extractUsername(claims);
            String stringAuthorities = jwtService.getSpecificClaim(claims, "authorities");

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | ParseException | JOSEException e) {
            logger.error(token, e);
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }
}



