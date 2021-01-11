package com.flow.tasks.transform;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class UnionTask implements ITask<Dataset<Row>> {

    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        Dataset<Row> dataset1 = (Dataset<Row>) flowContext.getResponse(ins[0]);
        Dataset<Row> dataset2 = (Dataset<Row>) flowContext.getResponse(ins[1]);
        return dataset1.union(dataset2);
    }
}
