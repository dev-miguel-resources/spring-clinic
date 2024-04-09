package com.spring.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    // te devuelve un conversor de datos
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
}
