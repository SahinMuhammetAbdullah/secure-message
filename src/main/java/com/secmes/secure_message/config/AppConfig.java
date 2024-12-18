package com.secmes.secure_message.config;

import com.secmes.secure_message.service.RSAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RSAService rsaService() {
        return new RSAService();
    }
}