package com.flow.services.components;

import io.kubernetes.client.openapi.ApiCallback;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Yaml;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class KubeWorkspacePaletteManager implements IWorkspacePaletteManager {

    private final AppsV1Api appsV1Api;
    private final ClassPathResource deploymentResource;
    private final ClassPathResource serviceResource;
    private final ClassPathResource ingressResource;
    private final CoreV1Api coreV1Api;
    private final ExtensionsV1beta1Api extensionsV1Beta1Api;
    private final RestTemplate restTemplate;
    private final ClassPathResource headLessServiceResource;

    public KubeWorkspacePaletteManager() throws IOException {
        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(1))
                .setReadTimeout(Duration.ofSeconds(1))
                .build();
        ApiClient apiClient = Config.defaultClient();
        Configuration.setDefaultApiClient(apiClient);
        appsV1Api = new AppsV1Api(apiClient);
        coreV1Api = new CoreV1Api();
        extensionsV1Beta1Api = new ExtensionsV1beta1Api();
        deploymentResource = new ClassPathResource("palette-kube-deloyment.yml");
        serviceResource = new ClassPathResource("palette-kube-service.yml");
        headLessServiceResource = new ClassPathResource("palette-kube-headless-service.yml");
        ingressResource = new ClassPathResource("palette-kube-ingress.yml");
    }

    @Override
    public void start(Long workspaceId) throws IOException, ApiException {
        String name = "data-palette-" + workspaceId;
        V1Deployment v1Deployment = (V1Deployment) Yaml.load(this.deploymentResource.getFile());
        V1Service v1Service = (V1Service) Yaml.load(serviceResource.getFile());
        V1Service headLessService = (V1Service) Yaml.load(headLessServiceResource.getFile());
        ExtensionsV1beta1Ingress extensionsV1beta1Ingress = (ExtensionsV1beta1Ingress) Yaml.load(ingressResource.getFile());
        Map<String, String> labels = new HashMap<>();
        labels.put("app.kubernetes.io/instance", "palette");
        labels.put("app.kubernetes.io/name", name);

        // Deployment overrides

        Objects.requireNonNull(v1Deployment.getMetadata()).setName(name);
        Objects.requireNonNull(v1Deployment.getMetadata()).setLabels(labels);
        Objects.requireNonNull(v1Deployment.getSpec()).getSelector().setMatchLabels(labels);
        Objects.requireNonNull(Objects.requireNonNull(v1Deployment.getSpec()).getTemplate().getMetadata()).setLabels(labels);
        V1Container v1Container = Objects.requireNonNull(Objects.requireNonNull(v1Deployment.getSpec()).getTemplate().getSpec()).getContainers().get(0);
        Objects.requireNonNull(v1Deployment.getSpec().getTemplate().getSpec()).setHostname(name);
        v1Container.setName(name);

        // service overrides
        Objects.requireNonNull(v1Service.getMetadata()).setName(name);
        Objects.requireNonNull(v1Service.getSpec()).setSelector(labels);

        // ingress overrides
        Objects.requireNonNull(extensionsV1beta1Ingress.getMetadata()).setName(name);
        extensionsV1beta1Ingress.getMetadata().setLabels(labels);
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(extensionsV1beta1Ingress.getSpec()).getRules()).get(0).getHttp()).getPaths().get(0).setPath("/" + workspaceId);
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(extensionsV1beta1Ingress.getSpec()).getRules()).get(0).getHttp()).getPaths().get(0).getBackend().setServiceName(name);

        appsV1Api.createNamespacedDeploymentAsync("default", v1Deployment, null, null, null, new ApiCallback<V1Deployment>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                e.printStackTrace();
            }

            @Override
            public void onSuccess(V1Deployment result, int statusCode, Map<String, List<String>> responseHeaders) {
                try {
                    coreV1Api.createNamespacedService("default", headLessService, null, null, null);
                    coreV1Api.createNamespacedService("default", v1Service, null, null, null);
                    extensionsV1Beta1Api.createNamespacedIngress("default", extensionsV1beta1Ingress, null, null, null);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }

    @Override
    public boolean isRunning(Long workspaceId) {
       /* final boolean[] running = {false};
        V1ReplicaSetList replicaSetList = this.appsV1Api.listNamespacedReplicaSet("default", null, null, null, null, null, null, null, null, null);
        if (replicaSetList.getItems().size() > 0) {
            replicaSetList.getItems().forEach(v1ReplicaSet -> {
                if (null != v1ReplicaSet.getMetadata()) {
                    if (MapUtils.isNotEmpty(v1ReplicaSet.getMetadata().getLabels())) {
                        String name = v1ReplicaSet.getMetadata().getLabels().get("app.kubernetes.io/name");
                        if (("data-palette-" + workspaceId).equalsIgnoreCase(name)) {
                            if (null != v1ReplicaSet.getStatus()) {
                                if (v1ReplicaSet.getStatus().getReadyReplicas() > 0) {
                                    running[0] = true;
                                }
                            }
                        }
                    }
                }
            });
        }
        return running[0];*/
        try {
            ResponseEntity<Boolean> responseEntity = this.restTemplate.getForEntity("http://data-palette.local/" + workspaceId + "/platform/palette/status", Boolean.class);
            return responseEntity.getStatusCodeValue() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void stop(Long workspaceId) {
        V1Status status = null;
        try {
            status = extensionsV1Beta1Api.deleteNamespacedIngress("data-palette-" + workspaceId, "default", null, null, null, null, null, null);
            if ("Success".equalsIgnoreCase(status.getStatus())) {
                status = coreV1Api.deleteNamespacedService("data-palette-" + workspaceId, "default", null, null, null, null, null, null);
                if ("Success".equalsIgnoreCase(status.getStatus())) {
                    appsV1Api.deleteNamespacedDeployment("data-palette-" + workspaceId, "default", null, null, null, null, null, null);
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
