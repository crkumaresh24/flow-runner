package com.stacksnow.flow.services.repositories;

import com.stacksnow.flow.services.models.Flow;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface IFlowRepository extends CrudRepository<Flow, UUID> {
}
