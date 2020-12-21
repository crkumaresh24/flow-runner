package com.stacksnow.flow.services;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.stacksnow.flow.services.models.DAG;
import com.stacksnow.flow.services.models.Flow;
import com.stacksnow.flow.services.models.Process;
import com.stacksnow.flow.services.models.Status;
import com.stacksnow.flow.services.repositories.IFlowRepository;
import com.stacksnow.flow.services.repositories.IProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {

    private final IFlowRepository flowRepository;
    private final IProcessRepository processRepository;
    private final LivyRunner livyRunner;
    private final AmazonS3 s3;

    @Autowired
    public Service(IFlowRepository flowRepository, IProcessRepository processRepository, LivyRunner livyRunner) {
        this.flowRepository = flowRepository;
        this.processRepository = processRepository;
        this.livyRunner = livyRunner;
        s3 = AmazonS3ClientBuilder
                .standard()
                .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                .withRegion(Regions.DEFAULT_REGION).build();
    }

    public Iterable<Flow> list() {
        return this.flowRepository.findAll();
    }

    public Flow create(String name, DAG dag) {
        return this.flowRepository.save(new Flow(dag, name));
    }

    public Process run(String id) throws URISyntaxException {
        Flow flow = read(id);
        Process process = new Process();
        process.setFlow(flow);
        process.setStatus(Status.NOT_STARTED);
        long livyJobId = this.livyRunner.run(id);
        process.setLivyJobId(livyJobId);
        return this.processRepository.save(process);
    }

    public Flow read(String id) {
        return this.flowRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);
    }

    public Flow update(String id, DAG dag) {
        Flow flow = read(id);
        Flow updateFlow = new Flow();
        updateFlow.setId(flow.getId());
        updateFlow.setDag(dag);
        updateFlow.setName(flow.getName());
        return this.flowRepository.save(updateFlow);
    }

    public void delete(String id) {
        Flow flow = read(id);
        this.processRepository.findAllByFlow(flow).forEach(process -> {
            killProcess(process.getId().toString());
        });
        this.flowRepository.delete(flow);
    }

    public Iterable<Process> processes() {
        return this.processRepository.findAll();
    }

    public void killProcess(String id) {
        this.processRepository.deleteById(UUID.fromString(id));
    }

    public Process updateProcessStatus(String id, Status status) {
        Process process = readProcess(id);
        process.setStatus(status);
        return this.processRepository.save(process);
    }

    public Process readProcess(String id) {
        return this.processRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<String> buckets() {
        return s3.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
    }
}
