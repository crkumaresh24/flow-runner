package com.stacksnow.dataflow.tasks.transform;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class RowFilterTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        String filter = String.valueOf(request.get("filter"));
        return dataset.where(filter);
    }
}
