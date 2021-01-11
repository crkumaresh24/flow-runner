package com.flow.tasks.load;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.Map;

public class WriteESTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        Map<String, String> esOptions = new HashMap<>();
        request.forEach((key, value) -> esOptions.put(key.replaceAll("_", "."),
                String.valueOf(value).isEmpty() ? null : String.valueOf(value)));
        dataset.write().format("org.elasticsearch.spark.sql")
                .options(esOptions)
                .mode(esOptions.getOrDefault("mode", "error"))
                .save(String.valueOf(request.get("indexName")));
        return dataset;
    }
}
