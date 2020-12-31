package com.stacksnow.flow.runner.spark.java;

import java.util.HashMap;
import java.util.Map;

public class FlowContext {

    private String s3_bucket_name;

    FlowContext(String s3_bucket_name) {
        this.s3_bucket_name = s3_bucket_name;
    }

    private Map<String, Object> responseMap = new HashMap<>();

    public Object getResponse(String key) {
        return this.responseMap.get(key);
    }

    void putResponse(String key, Object object) {
        this.responseMap.put(key, object);
    }

    public String getAbsoluteFilePath(String path) {
        return "s3a://" + s3_bucket_name + "/files/" + path;
    }

    public String getAbsoluteTablePath(String path) {
        return "s3a://" + s3_bucket_name + "/tables/" + path;
    }

    public String getAbsoluteModelPath(String path) {
        return "s3a://" + s3_bucket_name + "/models/" + path;
    }
}
