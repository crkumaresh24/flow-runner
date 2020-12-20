package com.stacksnow.flow.services.repositories;

import com.stacksnow.flow.services.models.Flow;
import com.stacksnow.flow.services.models.Process;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProcessRepository extends CrudRepository<Process, UUID> {
    Iterable<Process> findAllByFlow(Flow flow);
}
