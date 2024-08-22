package com.spring.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable { // que es que solicito al usuario para autenticarse

    private String username;
    private String password;

}
