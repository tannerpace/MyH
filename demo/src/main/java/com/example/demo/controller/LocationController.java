package com.example.demo.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Validated // This enables validation for request parameters
public class LocationController {

    @GetMapping("/locations")
    public Map<String, Object> getLocations(
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int limit,
            @RequestParam(defaultValue = "0") @Min(0) int offset,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order,
            @RequestParam @Min(-90) @Max(90) double lat,
            @RequestParam @Min(-180) @Max(180) double lon) {

        // Mocking a location response
        List<Map<String, Object>> locations = Collections.singletonList(
                Map.of(
                        "location_id", 1,
                        "location_name", "Mock Location",
                        "description", "A mock description",
                        "location_address", "123 Mock St",
                        "location_img_url", "https://example.com/img.jpg",
                        "location_latitude", lat,
                        "location_longitude", lon,
                        "viable_directions", Map.of("N", true, "S", true, "E", true, "W", true),
                        "weather", Map.of("temp", 20, "description", "Sunny")
                )
        );

        return Map.of(
                "total", locations.size(),
                "locations", locations
        );
    }
}


