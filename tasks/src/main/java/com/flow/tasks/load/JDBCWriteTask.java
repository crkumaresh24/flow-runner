package com.flow.tasks.load;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.Map;

public class JDBCWriteTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        Map<String, String> jdbcParseOptions = new HashMap<>();
        request.forEach((key, value) -> jdbcParseOptions.put(key, String.valueOf(value)));
        dataset.write().format("jdbc")
                .mode(jdbcParseOptions.getOrDefault("mode", "error"))
                .options(jdbcParseOptions)
                .save();
        return dataset;
    }
}
