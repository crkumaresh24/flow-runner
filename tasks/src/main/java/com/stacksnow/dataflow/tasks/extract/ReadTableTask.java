package com.stacksnow.dataflow.tasks.extract;


import com.stacksnow.flow.runner.spark.java.cli.ITask;
import com.stacksnow.flow.runner.spark.java.model.FlowContext;

import java.util.Map;

public class ReadTableTask implements ITask<String> {
   /* @Override
    public Dataset<Row> execute(DataFlowContext dataFlowContext, List<Dataset> list, Map<String, Object> map) throws URISyntaxException {
        long dataTableId = Long.parseLong((String) map.get("tableId"));
        DataTableMetadata dataTableMetadata = dataFlowContext.getExplorerServices().getDataTableMetaData(dataFlowContext.getWorkspaceId(), dataTableId);
        return dataFlowContext.getSparkSession().read().parquet(dataFlowContext.getDataTableAbsolutePath(dataTableMetadata));
    }*/

    @Override
    public String execute(FlowContext flowContext, String[] set, Map<String, Object> map) throws Exception {
        return "Tokenizer";
    }
}
