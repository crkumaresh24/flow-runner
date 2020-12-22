package com.stacksnow.dataflow.tasks.ml;

import com.stacksnow.flow.runner.spark.java.cli.ITask;
import com.stacksnow.flow.runner.spark.java.contextmanagers.SparkFlowContext;
import com.stacksnow.flow.runner.spark.java.model.FlowContext;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class LinearRegressionTask implements ITask<LinearRegressionModel> {
    @Override
    public LinearRegressionModel execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        SparkFlowContext context = (SparkFlowContext) flowContext;
        Dataset<Row> training = context.getSparkSession().read().format("libsvm")
                .load("file:///Users/kchenniappanramak/Documents/Personal/github/flow-runner-spark-cluster-java/static-server/files/sample_linear_regression_data.txt");

        LinearRegression lr = new LinearRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model.
        return lr.fit(training);
    }
}
