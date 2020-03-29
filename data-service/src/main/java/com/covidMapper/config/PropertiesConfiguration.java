package com.covidMapper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Value("${fetchHotSpots.url}")
    public String fetchHotSpotsUrl;

}
