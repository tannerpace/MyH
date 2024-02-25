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
                .defaultHeader("Accept", "application/json") // Ensure correct Accept header
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Contact-Email", "newtanner29@gmail.com")
                .build();
    }
    public Mono<String> getWeather(double lat, double lon) {
      String path = String.format("/points/%.4f,%.4f", lat, lon);
      return this.webClient.get()
              .uri(uriBuilder -> uriBuilder.path(path).build())
              .exchangeToMono(response -> {
                  if (response.statusCode().is2xxSuccessful()) {
                      return response.bodyToMono(String.class);
                  } else {
                      // Log, handle or ignore the error based on the status code or error payload
                      // For now, let's just log and return an empty Mono
                      return response.bodyToMono(String.class).doOnNext(body -> {
                          // Log the error body or handle it
                          System.out.println("Error response body: " + body);
                      }).then(Mono.empty()); // Consumes the error body and returns an empty Mono
                  }
              })
              .onErrorResume(e -> {
                  System.out.println("Error fetching weather: " + e.getMessage());
                  return Mono.empty(); // Handle exceptions thrown during the exchange
              });
  }  
  }

//     public Mono<String> getWeather(double lat, double lon) {
//         String path = String.format("/points/%.4f,%.4f", lat, lon);
//         return this.webClient.get().uri(uriBuilder -> uriBuilder.path(path).build())
//                 .retrieve()
//                 .onStatus(status -> status.isError(), response -> {
//                     // Log or handle specific error statuses here
//                     return Mono.error(new RuntimeException("Error with the weather API"));
//                 })
//                 .bodyToMono(String.class)
//                 .onErrorResume(e -> {
//                     // Log error
//                     System.out.println("Error fetching weather: " + e.getMessage());
//                     // Return an empty Mono or a default value to gracefully handle the error
//                     return Mono.empty();
//                 });
//     }
// }
