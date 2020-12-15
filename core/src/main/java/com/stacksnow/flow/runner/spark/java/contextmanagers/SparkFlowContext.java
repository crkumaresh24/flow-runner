package com.stacksnow.flow.runner.spark.java.contextmanagers;

import com.stacksnow.flow.runner.spark.java.model.FlowContext;
import org.apache.spark.sql.SparkSession;

public class SparkFlowContext extends FlowContext {
    private SparkSession sparkSession;

    public SparkSession getSparkSession() {
        // final String master = "k8s://https://kubernetes.docker.internal:6443";
        final String master = "local[*]";
        if (null == this.sparkSession) {
            this.sparkSession = SparkSession.builder().master(master).getOrCreate();
        }
        return this.sparkSession;
    }
}
