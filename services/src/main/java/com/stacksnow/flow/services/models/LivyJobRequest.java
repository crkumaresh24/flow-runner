package com.stacksnow.flow.services.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LivyJobRequest {
    private String file;
    private String className;
    private List<String> jars = new ArrayList<>();
    private List<String> args = new ArrayList<>();

    public LivyJobRequest(String staticServerURL, String flowServerURL, String flowId) {
        this.file = staticServerURL + "/core-1.0-SNAPSHOT.jar";
        this.className = "com.stacksnow.flow.runner.spark.java.App";
        jars.add(staticServerURL + "/tasks-1.0-SNAPSHOT.jar");
        jars.add(staticServerURL + "/gson-2.2.4.jar");
        jars.add(staticServerURL + "/postgresql-42.2.9.jar");
        jars.add(staticServerURL + "/jgrapht-core-1.0.1.jar");
        args.add(staticServerURL + "/runnerList.json");
        args.add(flowServerURL + "/flow/" + flowId);
    }
}
