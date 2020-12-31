package com.stacksnow.dataflow.tasks.transform;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class ColumnFilterTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        String[] columns = String.valueOf(request.get("columns")).split(",");
        for (int i = 0; i < columns.length; i++) {
            dataset = dataset.select(columns[i]);
        }
        return dataset;
    }
}
