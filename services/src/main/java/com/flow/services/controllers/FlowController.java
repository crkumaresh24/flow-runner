package com.flow.services.controllers;

import com.flow.services.controllers.requests.CreateFlow;
import com.flow.services.models.Flow;
import com.flow.services.models.Process;
import com.flow.services.services.FlowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/api/flows")
@CrossOrigin(origins = {"http://localhost:3000"})
@Tag(name = "Flows Web Services", description = "Exposes RESTFul services to manage(create, read, update, run, delete) flows")
public class FlowController {

    private final FlowService flowService;

    @Autowired
    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

    @GetMapping
    public Iterable<Flow> list() {
        return this.flowService.list();
    }

    @GetMapping("/buckets")
    public Iterable<String> buckets() {
        return this.flowService.buckets();
    }

    @PostMapping
    public Flow create(@RequestBody CreateFlow createFlow) {
        return this.flowService.create(createFlow.getName(), createFlow.getDag(), createFlow.getJobProperties());
    }

    @PostMapping("/run/{id}")
    public Process run(@PathVariable("id") String id) throws URISyntaxException {
        return this.flowService.run(id);
    }

    @GetMapping("/{id}")
    public Flow read(@PathVariable("id") String id) {
        return this.flowService.read(id);
    }

    @PutMapping("/{id}")
    public Flow update(@PathVariable("id") String id, @RequestBody CreateFlow createFlow) {
        return this.flowService.update(id, createFlow.getDag(), createFlow.getJobProperties());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        this.flowService.delete(id);
    }
}
