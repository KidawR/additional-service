package com.example.additionalservice.service.clients;

import com.example.additionalservice.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebApiKillerClient {

    private final ApiProperties apiProperties;

    public void crashCoreService() {
        WebClient webClient = WebClient.create(apiProperties.getBaseUrl());
        webClient.post()
                .uri("/internal/crash")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
