package com.stacksnow.flow.services.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Data
public class Edge implements Cloneable, Serializable {
    private String key;
    private String source;
    private String target;
    private Map<String, Object> attributes;

    public Edge() {
    }

    public Edge(String key) {
        this.key = key;
    }

    public Edge(String key, String source, String target) {
        this.key = key;
        this.source = source;
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return key.equals(edge.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
