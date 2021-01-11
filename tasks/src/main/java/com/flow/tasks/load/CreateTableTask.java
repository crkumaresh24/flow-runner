package com.flow.tasks.load;

import com.stacksnow.flow.runner.spark.java.FlowContext;
import com.stacksnow.flow.runner.spark.java.cli.ITask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateTableTask implements ITask<Dataset<Row>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTableTask.class);

    /*@SneakyThrows
    @Override
    public Dataset<Row> execute(DataFlowContext context, List<Dataset> ins, Map<String, Object> request) {
        if (CollectionUtils.isNotEmpty(ins)) {
            String mode = String.valueOf(request.get("mode"));
            Boolean summarize = Boolean.valueOf(String.valueOf(request.get("summarizeNumericColumns")));
            DataTableMetadata dataTableMetadata = new DataTableMetadata();
            dataTableMetadata.setName(String.valueOf(request.get("tableName")));
            dataTableMetadata.setWorkspaceId(context.getWorkspaceId());
            dataTableMetadata.setPath("/" + dataTableMetadata.getName());
            DataTableMetadata metadataResponse = context.getExplorerServices().createDataTableMetaData(dataTableMetadata);
            Dataset<Row> dataset = convertArrayTypesToString(ins.get(0));
            String dest = context.getDataTableAbsolutePath(metadataResponse);
            context.deleteFileIfExists(dest);
            dataset = normalizeColumnNames(dataset);
            if (mode.equalsIgnoreCase("APPEND")) {
                dataset.write().mode(SaveMode.Append).parquet(dest);
            } else if (mode.equalsIgnoreCase("OVERWRITE")) {
                dataset.write().mode(SaveMode.Overwrite).parquet(dest);
            } else {
                dataset.write().mode(SaveMode.ErrorIfExists).parquet(dest);
            }
            List<ColumnMetadata> columnMetadataList = getColumns(dataset);
            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("colSize", columnMetadataList.size());
            updateFields.put("columns", columnMetadataList);
            updateFields.put("rowSize", metadataResponse.getRowSize() + dataset.count());
            updateFields.put("statusId", 6);
            if (summarize) {
                updateFields.put("summary", Utils.getQueryResponse(dataset.summary()).getResult());
                context.getExplorerServices().updateDataTableMetadata(metadataResponse.getWorkspaceId(), metadataResponse.getId(), updateFields);
            } else {
                updateFields.put("summary", null);
                context.getExplorerServices().updateDataTableMetadata(metadataResponse.getWorkspaceId(), metadataResponse.getId(), updateFields);
            }
            return dataset;
        }
        throw new RuntimeException("invalid ins to task - 'create table'");
    }*/

    @Override
    public Dataset<Row> execute(FlowContext flowContext, String[] ins, Map<String, Object> request) throws Exception {
        Dataset<Row> dataset = normalizeColumnNames((Dataset<Row>) flowContext.getResponse(ins[0]));
        String path = flowContext.getAbsoluteTablePath((String) request.get("tableName"));
        dataset.write()
                .mode(String.valueOf(request.getOrDefault("mode", "error")))
                .parquet(path);
        return dataset;
    }

    private Dataset<Row> normalizeColumnNames(Dataset<Row> dataset) {
        Map<String, String> toBeConverted = new HashMap<>();
        String[] columnsNames = dataset.schema().fieldNames();
        Pattern pattern = Pattern.compile("//s+");
        Arrays.stream(columnsNames).forEach(column -> {
            if (pattern.matcher(column).matches()) {
                toBeConverted.put(column, pattern.matcher(column).replaceAll("_"));
            }
        });
        for (Map.Entry<String, String> entrySet : toBeConverted.entrySet()) {
            dataset = dataset.withColumnRenamed(entrySet.getKey(), entrySet.getValue());
        }
        return dataset;
    }
}
