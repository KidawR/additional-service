package com.example.additionalservice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiProperties {
    @Value("${core.service.host}")
    private String coreServiceHost;

    @Value("${core.service.port}")
    private String coreServicePort;

    @Value("${service.statistic.delay:5000}")
    private int delay;
    private String baseUrl;
    public String getBaseUrl() {
        return "http://" + coreServiceHost + ":" + coreServicePort;
    }
}
