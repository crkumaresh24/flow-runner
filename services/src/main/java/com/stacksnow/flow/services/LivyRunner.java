package com.stacksnow.flow.services;

import com.stacksnow.flow.services.controllers.responses.LivyLogResponse;
import com.stacksnow.flow.services.models.LivyJobRequest;
import com.stacksnow.flow.services.models.LivyJobResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class LivyRunner {

    private final RestTemplate restTemplate;

    @Value("${livy.url:http://localhost:8998}")
    private String LIVY_URL;

    @Value("${tasks.url:http://localhost:9090/tasks}")
    private String TASKS_SERVICES_URL;

    @Value("${jars.url:http://localhost:9090/flows}")
    private String JARS_SERVICES_URL;

    @Value("${flows.url:http://localhost:9090/flows}")
    private String FLOWS_SERVICES_URL;

    @Value("${processes.url:http://localhost:9090/processes}")
    private String PROCESSES_SERVICES_URL;

    @Value("${lake.s3BucketName:mlops-lake}")
    private String LAKE_S3_BUCKET_NAME;

    public LivyRunner() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public long run(String processId) throws URISyntaxException {
        LivyJobRequest livyJobRequest = new LivyJobRequest(TASKS_SERVICES_URL, JARS_SERVICES_URL, PROCESSES_SERVICES_URL, LAKE_S3_BUCKET_NAME, processId);
        RequestEntity<LivyJobRequest> livyJobRequestRequestEntity = new RequestEntity<>(livyJobRequest, HttpMethod.POST, new URI(LIVY_URL + "/batches"));
        System.out.println(livyJobRequest);
        ResponseEntity<LivyJobResponse> response = this.restTemplate.exchange(livyJobRequestRequestEntity, LivyJobResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getId();
        }
        throw new RuntimeException("job submission failed");
    }

    public boolean kill(long livyJobId) throws URISyntaxException {
        RequestEntity<LivyJobRequest> requestEntity = new RequestEntity<>(HttpMethod.DELETE, new URI(LIVY_URL + "/batches/" + livyJobId));
        try {
            ResponseEntity<String> response = this.restTemplate.exchange(requestEntity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return e instanceof HttpClientErrorException.NotFound;
        }
    }

    public LivyLogResponse logs(long livjJobId) throws URISyntaxException {
        return this.restTemplate.getForEntity(LIVY_URL + "/batches/" + livjJobId + "/log", LivyLogResponse.class).getBody();
    }
}
