package com.stacksnow.flow.runner.spark.java.model;

import lombok.Data;

@Data
public class Process {
    private Flow flow;
    private String id;
    private String status;
}
