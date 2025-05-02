package com.example.additionalservice.service.clients;


import com.example.additionalservice.ApiProperties;
import com.example.additionalservice.model.Ticket;
import com.example.additionalservice.service.statistics.ObservabilityService;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TicketClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ApiProperties apiProperties;
    private final ObservabilityService observabilityService;
    public TicketClient(ApiProperties apiProperties, ObservabilityService observabilityService) {
        this.apiProperties = apiProperties;
        this.observabilityService = observabilityService;
    }

    public Ticket[] getAllTickets() {
        this.observabilityService.start(getClass().getSimpleName() + ":getAllTickets");
        Ticket[] temp = restTemplate.getForObject(apiProperties.getBaseUrl() + "/tickets", Ticket[].class);
        this.observabilityService.stop(getClass().getSimpleName() + ":getAllTickets");
        return temp;
    }

}