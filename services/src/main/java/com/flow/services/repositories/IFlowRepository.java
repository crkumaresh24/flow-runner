package com.flow.services.repositories;

import com.flow.services.models.Flow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IFlowRepository extends CrudRepository<Flow, UUID> {
}
