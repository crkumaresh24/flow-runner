package com.flow.tasks.ml;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.SparkFlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class KMeansClassificationTask implements ITask<KMeansModel> {
    @Override
    public KMeansModel execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        SparkFlowContext context = (SparkFlowContext) flowContext;
        String trainingFilePath = flowContext.getAbsoluteFilePath((String) request.get("trainingFilePath"));
        Dataset<Row> training = context.getSparkSession().read().format("libsvm").load(trainingFilePath);
        KMeans kmeans = new KMeans()
                .setK(Integer.parseInt((String) request.get("kValue")))
                .setSeed(Long.parseLong((String) request.get("seed")));
        // Fit the model.
        return kmeans.fit(training);
    }
}
