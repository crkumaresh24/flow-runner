package com.stacksnow.flow.services;

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

@org.springframework.stereotype.Service
public class Service {

    private final IFlowRepository flowRepository;
    private final IProcessRepository processRepository;
    private final LivyRunner livyRunner;

    @Autowired
    public Service(IFlowRepository flowRepository, IProcessRepository processRepository, LivyRunner livyRunner) {
        this.flowRepository = flowRepository;
        this.processRepository = processRepository;
        this.livyRunner = livyRunner;
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
}
