package com.covidMapper.repository;

import com.covidMapper.domain.ViewCount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewCountRepository extends MongoRepository<ViewCount, String> {
}
