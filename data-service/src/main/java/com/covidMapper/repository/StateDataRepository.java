package com.covidMapper.repository;

import com.covidMapper.domain.StateData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDataRepository extends MongoRepository<StateData,String> {
}
