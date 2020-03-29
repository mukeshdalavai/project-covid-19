package com.covidMapper.repository;

import com.covidMapper.domain.HotSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotSpotRepository extends MongoRepository<HotSpot,String> {
}
