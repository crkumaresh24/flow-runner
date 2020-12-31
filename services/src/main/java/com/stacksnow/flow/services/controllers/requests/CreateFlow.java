package com.stacksnow.flow.services.controllers.requests;

import com.stacksnow.flow.services.models.DAG;
import lombok.Data;

@Data
public class CreateFlow {
    private String name;
    private DAG dag;
}
