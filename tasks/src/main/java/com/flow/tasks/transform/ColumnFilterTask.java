package com.flow.tasks.transform;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.commons.lang.ArrayUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Arrays;
import java.util.Map;

public class ColumnFilterTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        String[] columns = String.valueOf(request.get("columns")).split(",");
        String[] trimmedColumnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            trimmedColumnNames[i] = columns[i].trim();
        }
        if (columns.length > 1) {
            Arrays.asList((String[]) ArrayUtils.subarray(trimmedColumnNames, 1, columns.length)).forEach(System.out::println);
            dataset = dataset.select(trimmedColumnNames[0], (String[]) ArrayUtils.subarray(trimmedColumnNames, 1, columns.length));
        } else {
            dataset = dataset.select(trimmedColumnNames[0]);
        }
        return dataset;
    }
}
