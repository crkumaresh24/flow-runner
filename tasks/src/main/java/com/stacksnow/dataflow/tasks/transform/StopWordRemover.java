package com.stacksnow.dataflow.tasks.transform;

import com.stacksnow.flow.runner.spark.java.cli.ITask;
import com.stacksnow.flow.runner.spark.java.model.FlowContext;
import org.apache.spark.ml.feature.StopWordsRemover;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class StopWordRemover implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> map) {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        dataset.printSchema();
        StopWordsRemover stopWordsRemover = new StopWordsRemover()
                .setInputCol((String) map.get("inputCol"))
                .setOutputCol((String) map.get("outputCol"));
        return stopWordsRemover.transform(dataset);
    }
}
