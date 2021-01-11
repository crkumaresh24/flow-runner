package com.flow.services.controllers.requests;

import com.flow.services.models.DAG;
import lombok.Data;

import java.util.Map;

@Data
public class CreateFlow {
    private String name;
    private DAG dag;
    private Map<String, String> jobProperties;
}
