package com.flow.tasks.load;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.Map;

public class WriteLibSVMTask implements ITask<Dataset> {
    @Override
    public Dataset execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        Map<String, String> parserOptions = new HashMap<>();
        request.forEach((key, value) -> parserOptions.put(key, String.valueOf(value)));
        String path = flowContext.getAbsoluteFilePath((String) request.get("path"));
        dataset.write().format("libsvm")
                .mode(parserOptions.getOrDefault("mode", "error"))
                .options(parserOptions)
                .save(path);
        return dataset;
    }
}
