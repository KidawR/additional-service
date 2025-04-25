package com.example.additionalservice.service.clients;
import com.example.additionalservice.ApiProperties;
import com.example.additionalservice.model.Artist;
import com.example.additionalservice.service.statistics.ObservabilityService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ArtistClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ApiProperties apiProperties;
    private final ObservabilityService observabilityService;
    public ArtistClient(ApiProperties apiProperties, ObservabilityService observabilityService) {
        this.apiProperties = apiProperties;
        this.observabilityService = observabilityService;
    }

    public Artist[] getAllArtists() {
        this.observabilityService.start(getClass().getSimpleName() + ":getStatsByArtist");
        Artist[] temp = restTemplate.getForObject(apiProperties.getBaseUrl() + "/artists", Artist[].class);
        this.observabilityService.stop(getClass().getSimpleName() + ":getStatsByArtist");
        return temp;
    }
}