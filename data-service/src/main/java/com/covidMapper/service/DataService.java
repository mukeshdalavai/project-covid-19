package com.covidMapper.service;

import com.covidMapper.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {

    void fetchStats();

    CountryData fetchDistrictsData(CountryData countryData);

    void fetchHotSpots();

    void saveStatsData(CountryData countryData);

    void insertStatsData();

    CountryData getStatsData() throws Exception;

    List<StateData> getStatesData() throws Exception;

    List<District> getDistrictsData() throws Exception;

    HotSpotWrapper getHotSpots() throws Exception;

    ViewCount getViews();

}
