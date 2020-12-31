package com.stacksnow.flow.runner.spark.java.cli;

import com.stacksnow.flow.runner.spark.java.FlowContext;

import java.util.Map;

public interface ITask<O> {
    O execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception;
}
