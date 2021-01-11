package com.flow.services.services;

import com.flow.services.LivyRunner;
import com.flow.services.controllers.responses.LivyLogResponse;
import com.flow.services.models.Process;
import com.flow.services.models.Status;
import com.flow.services.repositories.IProcessRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
public class ProcessService {

    private final IProcessRepository processRepository;
    private final LivyRunner livyRunner;

    public ProcessService(IProcessRepository processRepository, LivyRunner livyRunner) {
        this.processRepository = processRepository;
        this.livyRunner = livyRunner;
    }

    public Process create(Process process) {
        return this.processRepository.save(process);
    }

    public Iterable<Process> list() {
        return this.processRepository.findAll();
    }

    public void delete(String id) throws URISyntaxException {
        Process process = read(id);
        if (livyRunner.kill(process.getLivyJobId())) {
            this.processRepository.delete(process);
        } else {
            throw new RuntimeException("error while killing. please try again");
        }
    }

    public Process updateStatus(String id, Status status) {
        Process process = read(id);
        process.setStatus(status);
        return this.processRepository.save(process);
    }

    public Process read(String id) {
        return this.processRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
    }

    public String log(String id) throws URISyntaxException {
        LivyLogResponse logResponse = this.livyRunner.logs(read(id).getLivyJobId());
        return logResponse.getLog().stream().reduce((s1, s2) -> s1 + "<br />" + s2).get();
    }
}
