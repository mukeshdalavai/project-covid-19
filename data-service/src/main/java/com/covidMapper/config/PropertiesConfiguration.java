package com.covidMapper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Value("${fetchHotSpots.url}")
    public String fetchHotSpotsUrl;

    @Value("${fetchStats.url}")
    public String fetchStatsUrl;

    @Value("${fetchDistrictStats.url}")
    public String fetchDistrictStatsUrl;

    @Value("{$dataFile.location}")
    public String dataFileLocation;

}
