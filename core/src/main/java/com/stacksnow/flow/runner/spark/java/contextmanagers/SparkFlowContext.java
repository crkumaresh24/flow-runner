package com.stacksnow.flow.runner.spark.java.contextmanagers;

import com.stacksnow.flow.runner.spark.java.model.FlowContext;
import org.apache.spark.sql.SparkSession;

public class SparkFlowContext extends FlowContext {
    private SparkSession sparkSession;

    public SparkFlowContext() {
        this.sparkSession = SparkSession.builder().master("local[*]").getOrCreate();
    }

    public SparkSession getSparkSession() {
        return sparkSession;
    }
}
