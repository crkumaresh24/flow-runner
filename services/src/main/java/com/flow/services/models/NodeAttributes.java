package com.flow.services.models;

import lombok.Data;

import java.util.Map;

@Data
public class NodeAttributes {
    private String id;
    private String title;
    private String type;
    private Integer statusId;
    Map<String, Object> uiConfig;
    Map<String, Object> request;
    Map<String, Object> preRunResponse;
}
