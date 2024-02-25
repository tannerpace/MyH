package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .defaultHeader("User-Agent", "http://kitesurf.ninja")
                .defaultHeader("Accept", "application/cap+xml")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Contact-Email", "newtanner29@gmail.com");
    }
}
