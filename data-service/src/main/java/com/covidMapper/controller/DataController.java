package com.covidMapper.controller;
import com.covidMapper.domain.HotSpot;
import com.covidMapper.domain.HotSpotWrapper;
import com.covidMapper.service.DataService;
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

    @RequestMapping("/")
    public ResponseEntity<?> returnMsg() throws Exception
    {
        ResponseEntity responseEntity;

        responseEntity=new ResponseEntity<String>("It's Working", HttpStatus.ACCEPTED);
        System.out.println("HotSpots Successfully returned  : ");
        return  responseEntity;
    }

    @RequestMapping("get-hotspots")
    public ResponseEntity<?> getHotSpots() throws Exception
    {
        ResponseEntity responseEntity;
        
        responseEntity=new ResponseEntity<HotSpotWrapper>(dataService.getHotSpots(), HttpStatus.ACCEPTED);
        System.out.println("HotSpots Successfully returned  : ");
        return  responseEntity;
    }

    @RequestMapping("fetch-hotspots")
    public ResponseEntity<?> fetchHotSpots() throws Exception
    {
        ResponseEntity responseEntity;
        dataService.fetchHotSpots();
        responseEntity=new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
        System.out.println("HotSpots Successfully fetched  : ");
        return  responseEntity;
    }
}

