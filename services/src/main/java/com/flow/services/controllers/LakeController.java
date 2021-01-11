package com.flow.services.controllers;

import com.flow.services.services.LakeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/lake")
@CrossOrigin(origins = {"http://localhost:3000"})
@Tag(name = "Lake Web Services", description = "Exposes RESTFul services to list lake contents")
public class LakeController {

    private final LakeService lakeService;

    public LakeController(LakeService lakeService) {
        this.lakeService = lakeService;
    }

    @GetMapping("/files")
    public Iterable<String> files() {
        return this.lakeService.listFiles();
    }

    @GetMapping("/tables")
    public Iterable<String> tables() {
        return this.lakeService.listTables();
    }

    @GetMapping("/models")
    public Iterable<String> models() {
        return this.lakeService.listModels();
    }
}
