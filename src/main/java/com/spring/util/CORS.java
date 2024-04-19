package com.spring.util;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CORS implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // correr el filtro: activarlo
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin", "*"); // especificar que cualquier app nos pueda hacer peticiones
        response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, POST, PUT, OPTIONS"); // métodos permitods para http en nuestro servidor
        response.setHeader("Access-Control-Max-Age", "3600"); // tiempo de reconomiento que tienen los navegadores para identificar un recurso de ruta
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK); // health check: para saber si el servidor responde
        } else {
           chain.doFilter(req, res); 
        }
    }

    @Override
    public void destroy() {
        // método de ciclo de vida para dejar descansar al filtro cuando el servidor no es requerido
    }
    
}
