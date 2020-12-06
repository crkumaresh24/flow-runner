package com.stacksnow.flow.runner.spark.java.cli;

import com.stacksnow.flow.runner.spark.java.model.FlowContext;

import java.util.Map;

public class StopTask implements ITask<String> {

    @Override
    public String execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        return ins.toString();
    }
}
