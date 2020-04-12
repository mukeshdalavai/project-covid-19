package com.covidMapper.config;

import com.covidMapper.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Configuration
public class SchedulerConfiguration {

    @Autowired
    DataService dataService;
    static int x=0;
    @Scheduled(cron = "${cron.expression}")
    public void runFetchData() throws InterruptedException {
        System.out.println(new Date()+"==>Count="+(x++));
//        dataService.fetchStats();
//        dataService.fetchHotSpots();
    }
}
