package com.stacksnow.flow.services.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class LivyJobRequest {
    private String file;
    private String className;
    private List<String> jars = new ArrayList<>();
    private List<String> args = new ArrayList<>();
    private Map<String, String> conf = new HashMap<>();

    public LivyJobRequest(String tasksServiceUrl, String jarsUrl, String processesServiceUrl, String s3BucketName, String processId) {
        this.file = jarsUrl + "/core-1.0-SNAPSHOT.jar";
        this.className = "com.stacksnow.flow.runner.spark.java.FlowRunner";

        // spark-submit extra jars

        jars.add(jarsUrl + "/tasks-1.0-SNAPSHOT.jar");
        jars.add(jarsUrl + "/gson-2.2.4.jar");
        jars.add(jarsUrl + "/postgresql-42.2.9.jar");
        jars.add(jarsUrl + "/jgrapht-core-1.0.1.jar");

        // spark-submit configurations

        conf.put("spark.submit.deployMode", "cluster");
        conf.put("spark.kubernetes.container.image", "spark/spark:2.4.6");
        conf.put("spark.executor.instances", "1");
        conf.put("name", "processId-" + processId);
        conf.put("spark.kubernetes.driver.pod.name", "driver-" + processId + "-process");

        // spark-submit program arguments

        args.add(tasksServiceUrl);
        args.add(processesServiceUrl);
        args.add(s3BucketName);
        args.add(processId);
    }

    @Override
    public String toString() {
        return "LivyJobRequest{" +
                "file='" + file + '\'' +
                ", className='" + className + '\'' +
                ", jars=" + jars +
                ", args=" + args +
                '}';
    }
}
