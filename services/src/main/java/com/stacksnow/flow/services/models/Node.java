package com.stacksnow.flow.services.models;

import lombok.Data;

import java.util.Objects;

@Data
public class Node {
    private String key;
    private NodeAttributes attributes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return key.equals(node.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
