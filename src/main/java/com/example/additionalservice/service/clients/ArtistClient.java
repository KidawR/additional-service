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
    public Artist getArtistById(long id) {
        this.observabilityService.start(getClass().getSimpleName() + ":getArtistById");
        Artist temp = null;
        try {
            temp = restTemplate.getForObject(apiProperties.getBaseUrl() + "/artists/" + id, Artist.class);
        } catch (Exception e) {
            System.err.println("Failed to fetch artist with ID " + id + ": " + e.getMessage());
        }
        this.observabilityService.stop(getClass().getSimpleName() + ":getArtistById");
        return temp;
    }

}