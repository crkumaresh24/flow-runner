package com.stacksnow.flow.runner.spark.java.model;

import java.util.HashMap;
import java.util.Map;

public class FlowContext {
    private Map<String, Object> responseMap = new HashMap<>();

    public Object getResponse(String key) {
        return this.responseMap.get(key);
    }

    public void putResponse(String key, Object object) {
        this.responseMap.put(key, object);
    }
}
