package com.flow.services.repositories;

import com.flow.services.models.Flow;
import com.flow.services.models.Process;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProcessRepository extends CrudRepository<Process, UUID> {
    Iterable<Process> findAllByFlow(Flow flow);
}
