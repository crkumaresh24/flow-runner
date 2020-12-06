package com.stacksnow.dataflow.tasks.extract;

import com.stacksnow.flow.runner.spark.java.cli.ITask;
import com.stacksnow.flow.runner.spark.java.contextmanagers.SparkFlowContext;
import com.stacksnow.flow.runner.spark.java.model.FlowContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.Map;

public class ParseFileTask implements ITask<Dataset<Row>> {

   /* @Override
    public Dataset<Row> execute(DataFlowContext context, List<Dataset> ins, Map<String, Object> request) throws URISyntaxException {
        DataFileMetadata dataFileMetadata = context.getExplorerServices().getDataFileMetaData(context.getWorkspaceId(), Long.parseLong((String) request.get("fileId")));
        Map<String, String> csvParserOption = new HashMap<>();
        Map<String, Object> options = (Map<String, Object>) request.get("options");
        options.forEach((key, value) -> csvParserOption.put(key, String.valueOf(value)));
        return context.getSparkSession().read().options(csvParserOption).csv(context.getDataFileAbsolutePath(dataFileMetadata));
    }*/

    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] set, Map<String, Object> request) throws Exception {
        Map<String, String> csvParserOption = new HashMap<>();
        Map<String, Object> options = (Map<String, Object>) request.get("options");
        options.forEach((key, value) -> csvParserOption.put(key, String.valueOf(value)));
        SparkFlowContext context = (SparkFlowContext) flowContext;
        return context.getSparkSession().read().options(csvParserOption).csv(String.valueOf(request.get("path")));
    }
}
