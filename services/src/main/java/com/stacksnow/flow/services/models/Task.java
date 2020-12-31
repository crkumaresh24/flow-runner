package com.stacksnow.flow.services.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity(name = "tasks")
public class Task extends EntityWithUUID {
    private String taskName;
    private String className;

    public Task(String taskName, String className) {
        this.taskName = taskName;
        this.className = className;
    }
}
