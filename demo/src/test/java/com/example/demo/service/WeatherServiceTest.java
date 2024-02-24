package com.example.demo.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

class WeatherServiceTest {

    private MockWebServer mockWebServer;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        WebClient.Builder webClientBuilder = WebClient.builder().baseUrl(mockWebServer.url("/").toString());
        weatherService = new WeatherService(webClientBuilder);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getWeatherShouldReturnSuccessResponse() {
        String mockResponseBody = "{\"weather\":\"Sunny\"}";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody).addHeader("Content-Type", "application/json"));

        double lat = 40.712776;
        double lon = -74.005974;

        StepVerifier.create(weatherService.getWeather(lat, lon))
                .expectNextMatches(response -> response.contains("Sunny"))
                .verifyComplete();
    }

    @Test
    void getWeatherShouldHandleErrorResponseGracefully() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        double lat = 40.712776;
        double lon = -74.005974;

        StepVerifier.create(weatherService.getWeather(lat, lon))
                .verifyComplete();
    }
}
