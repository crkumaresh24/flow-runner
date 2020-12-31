package com.stacksnow.dataflow.tasks.extract;


import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.SparkFlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class ReadTableTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        String path = flowContext.getAbsoluteTablePath((String) request.get("tableName"));
        SparkFlowContext context = (SparkFlowContext) flowContext;
        return context.getSparkSession().read().parquet(path);
    }
}
