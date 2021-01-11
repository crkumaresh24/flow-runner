package com.flow.tasks.transform;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.ml.feature.RegexTokenizer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Collections;
import java.util.Map;

public class TokenizerTask implements ITask<Dataset<Row>> {
    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> map) {
        Dataset<Row> dataset = (Dataset<Row>) flowContext.getResponse(ins[0]);
        dataset.printSchema();
        RegexTokenizer tokenizer = new RegexTokenizer()
                .setGaps((Boolean) map.getOrDefault("gaps", false))
                .setPattern((String) map.get("pattern")).setInputCol((String) map.get("inputCol"))
                .setOutputCol((String) map.get("outputCol"));
        return tokenizer.transform(dataset.na().fill(Collections.singletonMap((String) map.get("inputCol"), "")));
    }
}
