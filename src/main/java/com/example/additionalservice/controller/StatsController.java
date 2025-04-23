package com.example.additionalservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/stats")
class StatsController {

    private final RestTemplate restTemplate;

    public StatsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<Object[]> getStats() {
        String url = "http://spring-boot-app:8080/tickets/stats";
        Object[] response = restTemplate.getForObject(url, Object[].class);
        return ResponseEntity.ok(response);
    }
}
