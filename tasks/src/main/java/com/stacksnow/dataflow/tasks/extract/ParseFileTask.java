package com.stacksnow.dataflow.tasks.extract;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.SparkFlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.Map;

public class ParseFileTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] set, Map<String, Object> request) throws Exception {
        Map<String, String> csvParserOption = new HashMap<>();
        Map<String, Object> options = (Map<String, Object>) request.get("options");
        options.forEach((key, value) -> csvParserOption.put(key, String.valueOf(value)));
        SparkFlowContext context = (SparkFlowContext) flowContext;
        String path = context.getAbsoluteFilePath((String) request.get("path"));
        csvParserOption.put("path", path);
        return context.getSparkSession().read().options(csvParserOption).csv(path);
    }
}
