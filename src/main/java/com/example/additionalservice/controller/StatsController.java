package com.example.additionalservice.controller;

import com.example.additionalservice.model.SectorStatsExtended;
import com.example.additionalservice.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;
    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/")
    public List<SectorStatsExtended> getStatsByArtist() {
        return statsService.getSectorStatsByArtistAndSector();
    }
}
