package com.stacksnow.flow.runner.spark.java.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Flow {
    private String id;
    private DAG dag;
    private String name;
}
