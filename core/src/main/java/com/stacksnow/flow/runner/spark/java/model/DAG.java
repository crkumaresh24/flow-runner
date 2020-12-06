package com.stacksnow.flow.runner.spark.java.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DAG {
    private List<Edge> edges;
    private List<Node> nodes;
    private Map<String, Object> attributes;
}
