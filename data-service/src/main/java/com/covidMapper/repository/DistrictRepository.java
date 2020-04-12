package com.covidMapper.repository;

import com.covidMapper.domain.District;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends MongoRepository<District, String> {
}
