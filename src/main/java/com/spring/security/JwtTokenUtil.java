package com.spring.security;

import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// una clase utilitaria para describir las características y comportamientos de los tokens de jwt
@Component
public class JwtTokenUtil implements Serializable {

    // mil.
    public final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 horas de vigencia

    @Value("${jwt.secret}") // EL Expression Lenguage -> definir clave de los tokens como firma
    private String secret;

    public String generateToken(UserDetails userDetails) {
        // definimos cual es la data que queremos agregrarle al token
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        claims.put("test", "spring-standard-jwt");

        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder() // definir su construcción final
                .setClaims(claims) // info.
                .setSubject(subject) // ref. al usuario
                .setIssuedAt(new Date(System.currentTimeMillis())) // fecha de creación del token
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // fecha de expiración
                .signWith(getSigninKey()) // la llave de firma
                .compact(); // devuelve la cadena del token con todo lo adherido
    }

    private Key getSigninKey() { // config. la definición de la llave
        return new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());
    }

    // utils
    public Claims getAllClaimsFromToken(String token) { // me devuelva el contenido del token (body)
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token); // esto me devuelve el payload
        return claimsResolver.apply(claims); // retornar el contenido resuelto desde claims
    }

    public String getUsernameFromToken(String token) { // obtener el username de las claims del token
        return getClaimFromToken(token, Claims::getSubject); // e-> e.getSubject();
    }

    public Date getExpirationDateFromToken(String token) { // obtenemos la fecha de expiración del token
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) { // para verificar si está expirado
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date()); // la fecha de expiración está antes que la fecha actual
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        // preguntar si el token pertenece a la sesión del usuario y si no ha expirado
        return (username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token));

    }

}
