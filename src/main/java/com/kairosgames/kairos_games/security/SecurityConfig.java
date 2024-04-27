package com.kairosgames.kairos_games.security;

import com.kairosgames.kairos_games.Jwt.JwtAuthenticationFilter;
import com.kairosgames.kairos_games.service.auth.JwtService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;
        

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

       return httpSecurity
               .csrf(AbstractHttpConfigurer::disable
               )
               .sessionManagement(sessionManager->
                       sessionManager
                               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET,"/api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/").permitAll()
                                .requestMatchers("/password/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/api/preferences/add/**").permitAll()
                                .requestMatchers("/api/games").permitAll()
                                .requestMatchers("/api/games/{id}").permitAll()
                                .requestMatchers("/api/games/filter/**").permitAll()
                                .requestMatchers("/api/games/trending").permitAll()
                                .anyRequest().authenticated())
               .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
               .exceptionHandling(exceptionHandling ->
                       exceptionHandling
                               .authenticationEntryPoint((request, response, authException) -> {
                                   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

                               }))
               .build();

   }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailList = new ArrayList<>();
        userDetailList.add(User.withUsername("Raquel")
                .password("1234")
                .roles("ADMIN")
                .authorities("ALL")
                .build());

        userDetailList.add(User.withUsername("David")
                .password("1234")
                .roles("USER")
                .build());

        return new InMemoryUserDetailsManager(userDetailList);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}