package com.stacksnow.flow.runner.spark.java;

import org.apache.spark.sql.SparkSession;

public class SparkFlowContext extends FlowContext {
    private SparkSession sparkSession;

    SparkFlowContext(String s3_bucket_name) {
        super(s3_bucket_name);
    }

    public SparkSession getSparkSession() {
        // final String master = "k8s://https://kubernetes.docker.internal:6443";
        final String master = "local[*]";
        if (null == this.sparkSession) {
            this.sparkSession = SparkSession.builder().master(master).getOrCreate();
            // https://hadoop.apache.org/docs/current/hadoop-aws/tools/hadoop-aws/index.html#General_S3A_Client_configuration
            this.sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.connection.ssl.enabled", "false");
            this.sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.aws.credentials.provider", "com.amazonaws.auth.DefaultAWSCredentialsProviderChain");
            this.sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.access.key", "");
            this.sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.secret.key", "");
        }
        return this.sparkSession;
    }
}
