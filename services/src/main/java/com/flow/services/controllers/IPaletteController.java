package com.flow.services.controllers;

import com.flow.services.components.IWorkspacePaletteManager;
import io.kubernetes.client.openapi.ApiException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api/palette")
@CrossOrigin(origins = {"http://localhost:3000"})
@Tag(name = "Palette Web Services", description = "Exposes RESTFul services to manage(start, stop) palette")
public class IPaletteController {
    private final IWorkspacePaletteManager workspacePaletteManager;

    public IPaletteController(IWorkspacePaletteManager workspacePaletteManager) {
        this.workspacePaletteManager = workspacePaletteManager;
    }

    @PostMapping("/start")
    public void startPalette() throws IOException, ApiException {
        this.workspacePaletteManager.start(1L);
    }

    @DeleteMapping("/stop")
    public void deletePalette() throws IOException, ApiException {
        this.workspacePaletteManager.stop(1L);
    }
}
