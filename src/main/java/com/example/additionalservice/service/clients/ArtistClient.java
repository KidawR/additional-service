package com.example.additionalservice.service.clients;
import com.example.additionalservice.ApiProperties;
import com.example.additionalservice.model.Artist;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ArtistClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ApiProperties apiProperties;

    public ArtistClient(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public Artist[] getAllArtists() {
        return restTemplate.getForObject(apiProperties.getBaseUrl() + "/artists", Artist[].class);
    }
}