package com.covidMapper.controller;
import com.covidMapper.domain.*;
import com.covidMapper.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1")
@CrossOrigin(origins = "*")
public class DataController {

    @Autowired
    DataService dataService;

    Logger logger = LoggerFactory.getLogger(DataController.class);

    @PostMapping("fetch-stats-data")
    public ResponseEntity<?> fetchStatsData() throws Exception
    {
        ResponseEntity responseEntity;
        dataService.fetchStats();
        responseEntity=new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
        logger.info("Stats Data Successfully Fetched from Government portal  : ");
        return  responseEntity;
    }

    @PostMapping("fetch-districts-data")
    public ResponseEntity<?> fetchDistrictsData() throws Exception
    {
        ResponseEntity responseEntity;
        dataService.fetchDistrictsData(new CountryData());
        responseEntity=new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
        logger.info("Districts Data Successfully Fetched from External portal  : ");
        return  responseEntity;
    }

    @PostMapping("fetch-hotSpots")
    public ResponseEntity<?> fetchHotSpots() throws Exception
    {
        ResponseEntity responseEntity;
        dataService.fetchHotSpots();
        responseEntity=new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
        logger.info("HotSpots Successfully fetched  : ");
        return  responseEntity;
    }

    @PostMapping("save-stats")
    public ResponseEntity<?> saveStatsData(@RequestBody CountryData countryData) throws Exception
    {
        ResponseEntity responseEntity;
        dataService.saveStatsData(countryData);
        responseEntity=new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
        logger.info("Stats Data Successfully inserted  : ");
        return  responseEntity;
    }

    @PostMapping("insert-stats-data")
    public ResponseEntity<?> insertStatsData() throws Exception
    {
        ResponseEntity responseEntity;
        dataService.insertStatsData();
        responseEntity=new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
        logger.info("Stats Data Successfully Inserted  : ");
        return  responseEntity;
    }

    @GetMapping("data")
    public ResponseEntity<?> getStatsData() throws Exception
    {
        ResponseEntity responseEntity;

        responseEntity=new ResponseEntity<CountryData>(dataService.getStatsData(), HttpStatus.ACCEPTED);
        logger.info("Stats Successfully returned  : ");
        return  responseEntity;
    }

    @GetMapping("state-data")
    public ResponseEntity<?> getStatesData(@RequestParam boolean districts, @RequestParam boolean dayReports) throws Exception{
        List<StateData> states = dataService.getStatesData();
        if (!districts){
            for (StateData state : states){
                state.setDistricts(null);
            }
        }
        if (!dayReports){
            for (StateData state : states){
                state.setDayReports(null);
            }
        }
        ResponseEntity responseEntity = new ResponseEntity<List<StateData>>(states, HttpStatus.ACCEPTED);
        logger.info("Successfully Returned States Data");
        return responseEntity;
    }

    @GetMapping("district-data")
    public ResponseEntity<?> getDistrictsData() throws Exception{
        ResponseEntity responseEntity = new ResponseEntity<List<District>>(dataService.getDistrictsData() , HttpStatus.ACCEPTED);
        logger.info("Successfully Returned Districts Data");
        return responseEntity;
    }

    @GetMapping("get-hotSpots")
    public ResponseEntity<?> getHotSpots() throws Exception
    {
        ResponseEntity responseEntity;
        responseEntity=new ResponseEntity<HotSpotWrapper>(dataService.getHotSpots(), HttpStatus.ACCEPTED);
        logger.info("HotSpots Successfully returned  : ");
        return  responseEntity;
    }

    @GetMapping("view-hits")
    public ResponseEntity<?> getViewCount(){
        ResponseEntity responseEntity = new ResponseEntity<ViewCount>(dataService.getViews() , HttpStatus.ACCEPTED);
        logger.info("Successfully Returned Page Hits");
        return responseEntity;
    }
}

