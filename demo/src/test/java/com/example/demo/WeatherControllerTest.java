package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class WeatherControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetWeather() {
        double lat = 40.7128; // Example latitude
        double lon = -74.0060; // Example longitude

        webTestClient.get().uri("/weather?lat=" + lat + "&lon=" + lon)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).consumeWith(response -> {
                    // Assert on the response
                    System.out.println("Response: " + response.getResponseBody());
                });
    }
}
