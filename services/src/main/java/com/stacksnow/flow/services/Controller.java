package com.stacksnow.flow.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacksnow.flow.services.models.DAG;
import com.stacksnow.flow.services.models.Flow;
import com.stacksnow.flow.services.models.Process;
import com.stacksnow.flow.services.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/flow")
@CrossOrigin(origins = {"http://localhost:3000"})
public class Controller {

    private final Service service;
    private final ObjectMapper objectMapper;

    @Autowired
    public Controller(Service service) {
        this.service = service;
        objectMapper = new ObjectMapper();
    }

    @GetMapping
    public Iterable<Flow> list() {
        return this.service.list();
    }

    @GetMapping("/processes")
    public Iterable<Process> processes() {
        return this.service.processes();
    }

    @PostMapping
    public Flow create(@RequestBody Map<String, Object> request) {
        DAG dag = this.objectMapper.convertValue(request.get("dag"), DAG.class);
        return this.service.create((String) request.get("name"), dag);
    }

    @PostMapping("/run/{id}")
    public Process run(@PathVariable("id") String id) throws URISyntaxException {
        return this.service.run(id);
    }

    @GetMapping("/{id}")
    public Flow read(@PathVariable("id") String id) {
        return this.service.read(id);
    }

    @GetMapping("/process/{id}")
    public Process readProcess(@PathVariable("id") String id) {
        return this.service.readProcess(id);
    }

    @PutMapping("/process/status/{id}/{status}")
    public Process updateProcessStatus(@PathVariable("id") String id, @PathVariable("status") Status status) {
        return this.service.updateProcessStatus(id, status);
    }

    @PutMapping("/{id}")
    public Flow update(@PathVariable("id") String id, @RequestBody DAG dag) {
        return this.service.update(id, dag);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        this.service.delete(id);
    }

    @DeleteMapping("/process/{id}")
    public void killProcess(@PathVariable("id") String id) {
        this.service.killProcess(id);
    }
}
