package com.tiendaonline.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderProviderSecurity {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        
        // Crea y configura un codificador de contraseñas BCrypt.
        // BCrypt es un algoritmo de hash seguro para almacenar contraseñas.
        return new BCryptPasswordEncoder();
        
    }
    
}
