package com.stacksnow.flow.services.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class DAG implements Serializable {
    private List<Edge> edges;
    private List<Node> nodes;
    private Map<String, Object> attributes;
    private Map<String, Object> options;
}
