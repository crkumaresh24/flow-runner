package com.stacksnow.flow.services.controllers;

import com.stacksnow.flow.services.controllers.requests.CreateTask;
import com.stacksnow.flow.services.models.Task;
import com.stacksnow.flow.services.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = {"http://localhost:3000"})
@Tag(name = "Tasks Web Services", description = "Exposes RESTFul services to manage(create, read, update, and delete) tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Iterable<Task> list() {
        return this.taskService.list();
    }

    @PostMapping
    public Task create(@RequestBody CreateTask createTask) {
        return this.taskService.createTask(createTask.getTaskName(), createTask.getClassName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.taskService.delete(id);
    }
}
