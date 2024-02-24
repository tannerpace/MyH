package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLocations_ReturnsLocationDetails() throws Exception {
        mockMvc.perform(get("/locations")
                        .param("lat", "45.0")
                        .param("lon", "90.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.locations[0].location_id").value(1))
                .andExpect(jsonPath("$.locations[0].location_name").value("Mock Location"))
                .andExpect(jsonPath("$.locations[0].description").value("A mock description"))
                .andExpect(jsonPath("$.locations[0].location_address").value("123 Mock St"))
                .andExpect(jsonPath("$.locations[0].location_img_url").value("https://example.com/img.jpg"))
                .andExpect(jsonPath("$.locations[0].location_latitude").value(45.0))
                .andExpect(jsonPath("$.locations[0].location_longitude").value(90.0))
                .andExpect(jsonPath("$.locations[0].viable_directions.N").value(true))
                .andExpect(jsonPath("$.locations[0].weather.temp").value(20))
                .andExpect(jsonPath("$.locations[0].weather.description").value("Sunny"));
    }
}
