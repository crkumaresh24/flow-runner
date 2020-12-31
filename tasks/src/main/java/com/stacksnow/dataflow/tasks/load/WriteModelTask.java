package com.stacksnow.dataflow.tasks.load;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.ml.util.GeneralMLWritable;

import java.util.Map;

public class WriteModelTask implements ITask<GeneralMLWritable> {
    @Override
    public GeneralMLWritable execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        GeneralMLWritable writable = (GeneralMLWritable) flowContext.getResponse(ins[0]);
        String modelName = (String) request.get("modelName");
        String path = flowContext.getAbsoluteModelPath(modelName);
        writable.save(path);
        return writable;
    }
}
