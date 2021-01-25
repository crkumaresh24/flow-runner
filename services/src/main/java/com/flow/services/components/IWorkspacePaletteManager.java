package com.flow.services.components;

import io.kubernetes.client.openapi.ApiException;

import java.io.IOException;

public interface IWorkspacePaletteManager {
    void start(Long workspaceId) throws IOException, ApiException;

    boolean isRunning(Long workspaceId);

    void stop(Long workspaceId) throws ApiException;
}
