package com.stacksnow.flow.services.controllers;

import com.stacksnow.flow.services.models.Process;
import com.stacksnow.flow.services.models.Status;
import com.stacksnow.flow.services.services.ProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/processes")
@CrossOrigin(origins = {"http://localhost:3000"})
@Tag(name = "Processes Web Services", description = "Exposes RESTFul services to read and kill flow processes")
public class ProcessController {

    private final ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping
    public Iterable<Process> processes() {
        return this.processService.list();
    }

    @GetMapping("/{id}/log")
    public String log(@PathVariable("id") String id) throws URISyntaxException {
        return this.processService.log(id);
    }

    @GetMapping("/{id}")
    public Process readProcess(@PathVariable("id") String id) {
        return this.processService.read(id);
    }

    @PutMapping("/status/{id}/{status}")
    public Process updateProcessStatus(@PathVariable("id") String id, @PathVariable("status") Status status) {
        return this.processService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void killProcess(@PathVariable("id") String id) throws URISyntaxException {
        this.processService.delete(id);
    }
}
