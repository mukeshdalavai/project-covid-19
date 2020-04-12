package com.covidMapper.repository;

import com.covidMapper.domain.CountryData;
import com.mongodb.Mongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryDataRepository extends MongoRepository<CountryData, String> {
}
