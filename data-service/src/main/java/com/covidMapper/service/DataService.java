package com.covidMapper.service;

import com.covidMapper.domain.HotSpot;
import com.covidMapper.domain.HotSpotWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {
    void fetchHotSpots();

    HotSpotWrapper getHotSpots() throws Exception;

}
