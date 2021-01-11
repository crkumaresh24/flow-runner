package com.flow.tasks.ml;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.SparkFlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class LinearRegressionTask implements ITask<LinearRegressionModel> {
    @Override
    public LinearRegressionModel execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        SparkFlowContext context = (SparkFlowContext) flowContext;
        String trainingFilePath = flowContext.getAbsoluteFilePath((String) request.get("trainingFilePath"));
        Dataset<Row> training = context.getSparkSession().read().format("libsvm").load(trainingFilePath);
        LinearRegression lr = new LinearRegression()
                .setMaxIter(Integer.parseInt((String) request.get("maxIter")))
                .setRegParam(Double.parseDouble((String) request.get("regParam")))
                .setElasticNetParam(Double.parseDouble((String) request.get("elasticNetParam")));

        // Fit the model.
        return lr.fit(training);
    }
}
