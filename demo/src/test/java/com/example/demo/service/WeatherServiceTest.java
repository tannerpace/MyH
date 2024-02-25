package com.example.demo.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

public class WeatherServiceTest {

    private MockWebServer mockWebServer;
    private WeatherService weatherService;
   

    @BeforeEach
    void setUp() throws IOException {
        // Start mock server
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        // Configure WebClient to use mock server
        WebClient.Builder webClientBuilder = WebClient.builder()
                                                      .baseUrl(mockWebServer.url("/").toString());
        // Initialize WeatherService with the WebClient
        weatherService = new WeatherService(webClientBuilder);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Shutdown mock server
        mockWebServer.shutdown();
    }

    @Test
    void getWeatherShouldReturnSuccessResponse() {
        // Setup mock server success scenario
          String mockResponseBody = """
          {
              "@context": [...], // The full context structure
              "id": "https://api.weather.gov/points/40.7127,-74.0059",
              "type": "Feature",
              "geometry": {
                  "type": "Point",
                  "coordinates": [-74.0059, 40.7127]
              },
              "properties": {
                  "@id": "https://api.weather.gov/points/40.7127,-74.0059",
                  "@type": "wx:Point",
                  "cwa": "OKX",
                  "forecastOffice": "https://api.weather.gov/offices/OKX",
                  "gridId": "OKX",
                  "gridX": 33,
                  "gridY": 35,
                  "forecast": "https://api.weather.gov/gridpoints/OKX/33,35/forecast",
                  "forecastHourly": "https://api.weather.gov/gridpoints/OKX/33,35/forecast/hourly",
                  "forecastGridData": "https://api.weather.gov/gridpoints/OKX/33,35",
                  "observationStations": "https://api.weather.gov/gridpoints/OKX/33,35/stations",
                  "relativeLocation": {
                      "type": "Feature",
                      "geometry": {
                          "type": "Point",
                          "coordinates": [-74.0279259, 40.745251]
                      },
                      "properties": {
                          "city": "Hoboken",
                          "state": "NJ",
                          "distance": {
                              "unitCode": "wmoUnit:m",
                              "value": 4067.6248563911
                          },
                          "bearing": {
                              "unitCode": "wmoUnit:degree_(angle)",
                              "value": 152
                          }
                      }
                  },
                  "forecastZone": "https://api.weather.gov/zones/forecast/NYZ072",
                  "county": "https://api.weather.gov/zones/county/NYC061",
                  "fireWeatherZone": "https://api.weather.gov/zones/fire/NYZ212",
                  "timeZone": "America/New_York",
                  "radarStation": "KDIX"
              }
          }
          """;
        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody)
                                                .addHeader("Content-Type", "application/json"));

        
        double lat = 40.7128;
        double lon = -74.0060;

        
        StepVerifier.create(weatherService.getWeather(lat, lon))
                    // .expectNextMatches(response -> response.contains(mockResponseBody))
                    .verifyComplete();
    }
  }

//     @Test
//     void getWeatherShouldHandleErrorResponseGracefully() {
//         // Setup mock server to return an error response
//         String mockResponseBody = """
//           {
//               "@context": [...], // The full context structure
//               "id": "https://api.weather.gov/points/40.7127,-74.0059",
//               "type": "Feature",
//               "geometry": {
//                   "type": "Point",
//                   "coordinates": [-74.0059, 40.7127]
//               },
//               "properties": {
//                   "@id": "https://api.weather.gov/points/40.7127,-74.0059",
//                   "@type": "wx:Point",
//                   "cwa": "OKX",
//                   "forecastOffice": "https://api.weather.gov/offices/OKX",
//                   "gridId": "OKX",
//                   "gridX": 33,
//                   "gridY": 35,
//                   "forecast": "https://api.weather.gov/gridpoints/OKX/33,35/forecast",
//                   "forecastHourly": "https://api.weather.gov/gridpoints/OKX/33,35/forecast/hourly",
//                   "forecastGridData": "https://api.weather.gov/gridpoints/OKX/33,35",
//                   "observationStations": "https://api.weather.gov/gridpoints/OKX/33,35/stations",
//                   "relativeLocation": {
//                       "type": "Feature",
//                       "geometry": {
//                           "type": "Point",
//                           "coordinates": [-74.0279259, 40.745251]
//                       },
//                       "properties": {
//                           "city": "Hoboken",
//                           "state": "NJ",
//                           "distance": {
//                               "unitCode": "wmoUnit:m",
//                               "value": 4067.6248563911
//                           },
//                           "bearing": {
//                               "unitCode": "wmoUnit:degree_(angle)",
//                               "value": 152
//                           }
//                       }
//                   },
//                   "forecastZone": "https://api.weather.gov/zones/forecast/NYZ072",
//                   "county": "https://api.weather.gov/zones/county/NYC061",
//                   "fireWeatherZone": "https://api.weather.gov/zones/fire/NYZ212",
//                   "timeZone": "America/New_York",
//                   "radarStation": "KDIX"
//               }
//           }
//           """;
//         mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        
//         double lat = 40.7127;
//         double lon = -74.0059;

        
//         StepVerifier.create(weatherService.getWeather(lat, lon))
//                     // .expectNextCount(0) // Expect no items emitted in case of error
//                     .expectNextMatches(response -> response.contains(mockResponseBody))
//                     .verifyComplete();
//     }
// }
