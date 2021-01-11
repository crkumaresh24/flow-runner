package com.flow.services.controllers.requests;

import lombok.Data;

@Data
public class CreateTask {
    private String taskName;
    private String className;
}
