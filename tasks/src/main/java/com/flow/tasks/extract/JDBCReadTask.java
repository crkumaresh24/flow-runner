package com.flow.tasks.extract;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.SparkFlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.Map;

public class JDBCReadTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] set, Map<String, Object> request) {
        Map<String, String> jdbcParseOptions = new HashMap<>();
        request.forEach((key, value) -> jdbcParseOptions.put(key, String.valueOf(value)));
        SparkFlowContext context = (SparkFlowContext) flowContext;
        return context.getSparkSession().read()
                .format("jdbc")
                .options(jdbcParseOptions)
                .load();
    }
}
