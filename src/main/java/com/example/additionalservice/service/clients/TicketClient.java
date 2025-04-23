package com.example.additionalservice.service.clients;


import com.example.additionalservice.ApiProperties;
import com.example.additionalservice.model.Ticket;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ApiProperties apiProperties;

    public TicketClient(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public Ticket[] getAllTickets() {
        return restTemplate.getForObject(apiProperties.getBaseUrl() + "/tickets", Ticket[].class);
    }
}