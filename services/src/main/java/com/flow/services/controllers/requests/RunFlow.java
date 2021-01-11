package com.flow.services.controllers.requests;

import lombok.Data;

import java.util.Map;

@Data
public class RunFlow {
    Map<String, String> processProperties;
}
