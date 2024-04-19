package com.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // habilita todo
@EnableMethodSecurity // habilitar ciertos validadores de authorization @PreAuthorize
@RequiredArgsConstructor
public class WebSecurityConfig { // esta clase centraliza todas las config. y las asocia a la app

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // obtiene todas las configs. de authentication
    }
    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // devoldemos una instancia de encriptación para pass
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwUserDetailsService).passwordEncoder(passwordEncoder()); // verificar que los pass en las solicitudes pertenecientes a un usuario se contrasten con el de bdd
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable) // deshabilitar protección de ataques en vistas desde el backend
            .authorizeHttpRequests(req -> req
                .requestMatchers("/login").permitAll() // ruta pública para todos los usuarios
                //.requestMatchers("/patients/**").permitAll() | .authenticated()
                .anyRequest().authenticated() // cualquier otra ruta que no sea login son privadas
            )
            .httpBasic(Customizer.withDefaults()) // protege a las rutas http de ataques de hackers
            .formLogin(AbstractHttpConfigurer::disable) // deshabilito el form login por defecto que trae spring
            .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(Customizer.withDefaults()); // habilitar este filtro en la sesión de usuarios

            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // comprobamos que antes de devolver cualquier recurso se asegure que necesite token o no
    
            return httpSecurity.build(); // retornamos el filtro de seguridad creado
    }
}
