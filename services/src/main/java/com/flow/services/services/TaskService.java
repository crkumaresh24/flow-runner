package com.flow.services.services;

import com.flow.services.models.Task;
import com.flow.services.repositories.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String taskName, String className) {
        Task task = new Task(taskName, className);
        return this.taskRepository.save(task);
    }

    public Iterable<Task> list() {
        return this.taskRepository.findAll();
    }

    public void delete(String taskId) {
        this.taskRepository.deleteById(UUID.fromString(taskId));
    }
}
