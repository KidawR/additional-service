package com.example.additionalservice.controller;
import com.example.additionalservice.model.SectorStatsExtended;
import com.example.additionalservice.service.ArtistCacheService;
import com.example.additionalservice.service.StatsService;
import com.example.additionalservice.service.statistics.ObservabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/stats")

public class StatsController {
    private final StatsService statsService;
    private final ObservabilityService observabilityService;
    private final ArtistCacheService artistCacheService;
    @Autowired
    public StatsController(ObservabilityService observabilityService, StatsService statsService, ArtistCacheService artistCacheService) {
        this.observabilityService = observabilityService;
        this.statsService = statsService;
        this.artistCacheService = artistCacheService;
    }

    @GetMapping("/")
    public List<SectorStatsExtended> getStatsByArtist() {
        this.observabilityService.start(getClass().getSimpleName() + ":getStatsByArtist");
        List<SectorStatsExtended> temp = statsService.getSectorStatsByArtistAndSector();
        this.observabilityService.stop(getClass().getSimpleName() + ":getStatsByArtist");
        return temp;
    }
    @PostMapping("/clear-cache")
    public ResponseEntity<String> clearCache() {
        artistCacheService.clearCache();
        return ResponseEntity.ok("Artist cache cleared");
    }

}
