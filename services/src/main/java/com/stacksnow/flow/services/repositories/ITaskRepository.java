package com.stacksnow.flow.services.repositories;

import com.stacksnow.flow.services.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskRepository extends CrudRepository<Task, UUID> {
}
