package com.flow.services.services;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.flow.services.LivyRunner;
import com.flow.services.models.DAG;
import com.flow.services.models.Flow;
import com.flow.services.models.Process;
import com.flow.services.models.Status;
import com.flow.services.repositories.IFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FlowService {

    private final IFlowRepository flowRepository;
    private final ProcessService processService;
    private final LivyRunner livyRunner;
    private final AmazonS3 s3;

    @Autowired
    public FlowService(IFlowRepository flowRepository, ProcessService processService, LivyRunner livyRunner) {
        this.flowRepository = flowRepository;
        this.processService = processService;
        this.livyRunner = livyRunner;
        s3 = AmazonS3ClientBuilder
                .standard()
                .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                .withRegion(Regions.DEFAULT_REGION).build();
    }

    public Iterable<Flow> list() {
        return this.flowRepository.findAll();
    }

    public Flow create(String name, DAG dag, Map<String, String> processProperties) {
        Flow flow = new Flow(dag, name);
        flow.setJobProperties(processProperties);
        return this.flowRepository.save(flow);
    }

    public Process run(String id) throws URISyntaxException {
        Flow flow = read(id);
        Process process = new Process();
        process.setFlow(flow);
        process.setStatus(Status.NOT_STARTED);
        process.setProcessProperties(flow.getJobProperties());
        long livyJobId = this.livyRunner.run(process);
        process.setLivyJobId(livyJobId);
        return this.processService.create(process);
    }

    public Flow read(String id) {
        return this.flowRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);
    }

    public Flow update(String id, DAG dag, Map<String, String> processProperties) {
        Flow flow = read(id);
        Flow updateFlow = new Flow();
        updateFlow.setId(flow.getId());
        updateFlow.setDag(dag);
        updateFlow.setName(flow.getName());
        updateFlow.setJobProperties(processProperties);
        return this.flowRepository.save(updateFlow);
    }

    public void delete(String id) {
        Flow flow = read(id);
        this.processService.list().forEach(process -> {
            try {
                this.processService.delete(process.getId().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
        this.flowRepository.delete(flow);
    }

    public Iterable<String> buckets() {
        return s3.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
    }
}
