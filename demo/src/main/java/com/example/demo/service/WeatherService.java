package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
      this.webClient = webClientBuilder
              .baseUrl("https://api.weather.gov")
              .defaultHeader("User-Agent", "http://kitesurf.ninja")
              .defaultHeader("Accept", "application/cap+xml")
              .defaultHeader("Content-Type", "application/json")
              .defaultHeader("Contact-Email", "newtanner29@gmail.com")
              .build();
  }


    public Mono<String> getWeather(double lat, double lon) {
      
        String path = String.format("/points/%.4f,%.4f", lat, lon);
        return this.webClient.get().uri(uriBuilder -> uriBuilder.path(path).build())
                .retrieve()
                .bodyToMono(String.class) // Change String.class to your weather response class
                .onErrorResume(e -> {
                    System.out.println("Error fetching weather: " + e.getMessage());
                    return Mono.empty(); // Handle the error scenario
                });
    }
}
