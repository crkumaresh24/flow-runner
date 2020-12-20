package com.stacksnow.flow.services;

import com.stacksnow.flow.services.models.LivyJobRequest;
import com.stacksnow.flow.services.models.LivyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class LivyRunner {

    private final RestTemplate restTemplate;

    @Value("${livy.url:http://localhost:8998}")
    private String LIVY_URL;

    @Value("${staticServer.url:http://localhost:8000}")
    private String STATIC_SERVER_URL;

    @Value("${staticServer.url:http://localhost:9090}")
    private String FLOW_SERVER_URL;

    public LivyRunner() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public long run(String flowId) throws URISyntaxException {
        LivyJobRequest livyJobRequest = new LivyJobRequest(STATIC_SERVER_URL, FLOW_SERVER_URL, flowId);
        RequestEntity<LivyJobRequest> livyJobRequestRequestEntity = new RequestEntity<>(livyJobRequest, HttpMethod.POST, new URI(LIVY_URL + "/batches"));
        ResponseEntity<LivyResponse> response = this.restTemplate.exchange(livyJobRequestRequestEntity, LivyResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getId();
        }
        throw new RuntimeException("job submission failed");
    }
}
