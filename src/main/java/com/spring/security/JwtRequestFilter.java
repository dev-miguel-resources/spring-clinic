/*package com.spring.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// interceptamos las solicitudes http para el manejo de los tokens
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (header != null) { // verificamos el header
            if (header.startsWith("Bearer ") || header.startsWith("bearer ")) {
                jwtToken = header.substring(7); // obtenemos el valor del token desde el header
                // jwtToken = header.split("")[1];

                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (Exception ex) {
                    request.setAttribute("exception", ex.getMessage());
                }
            } 
        }
        
        if (username != null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // vinculamos todas claims para la solicitud
                SecurityContextHolder.getContext().setAuthentication(userPasswordAuthToken); // habilita que luego autenticado el user pueda consultar si puede acceder a otros recursos
            }
        }

        filterChain.doFilter(request, response);
        
    } 
    
    
}*/
