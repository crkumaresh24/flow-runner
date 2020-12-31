package com.stacksnow.flow.runner.spark.java.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Task {
    private String id;
    private String taskName;
    private String className;
}
