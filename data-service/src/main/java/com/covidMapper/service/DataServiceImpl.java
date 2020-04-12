package com.covidMapper.service;
import com.covidMapper.config.PropertiesConfiguration;
import com.covidMapper.domain.*;
import com.covidMapper.repository.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    HotSpotRepository hotSpotRepository;

    @Autowired
    CountryDataRepository countryDataRepository;

    @Autowired
    StateDataRepository stateDataRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    ViewCountRepository viewCountRepository;

    @Autowired
    PropertiesConfiguration propertiesConfiguration;

    Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    @Override
    public void fetchStats() {
        try {
            URL url = new URL(propertiesConfiguration.fetchStatsUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            StringBuilder data = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String currentLine = "";
            while ((currentLine = br.readLine())!=null){
                data = data.append(currentLine);
            }

            logger.info("Printing raw Govt. Data : " + data.toString()+"\n");

            Gson gson = new Gson();
            GovStats govStats = gson.fromJson(data.toString(), GovStats.class);
            CountryData countryData = countryDataRepository.findById("India").get();
            List<StateData> states = countryData.getStates();
            Map<String, StateData> statesMap = new HashMap<>();

            // Printing the created object
            logger.info("Printing parsed Govt. Data : " + govStats.toString()+"\n");

            int totalConfirmed = 0;
            int totalRecovered = 0;
            int totalDeaths = 0;
            int totalActive = 0;

            if (govStats.getRecords().size() > 0) {
                for (Record row : govStats.getRecords()) {
                    boolean existingState = false;
                    StateData stateData = new StateData();
                    for (StateData state : states) {
                        if (row.getNameOfStateUt().equalsIgnoreCase("Telengana")) {
                            row.setNameOfStateUt("Telangana");
                        }
                        if (state.getName().equalsIgnoreCase(row.getNameOfStateUt())) {
                            existingState = true;
                            stateData = state;
                        }
                    }
                    if (existingState) {
                        List<DayReport> dayReportList = stateData.getDayReports();
                        DayReport report = convertIntoReport(row);
                        if (dayReportList.get(dayReportList.size() - 1).getDate().toString().equalsIgnoreCase(report.getDate().toString())) {
                            dayReportList.remove(dayReportList.size() - 1);
                        }
                        dayReportList.add(report);
                        stateData.setConfirmed(report.getConfirmed());
                        stateData.setRecovered(report.getRecovered());
                        stateData.setDeaths(report.getDeaths());
                        stateData.setActive(report.getActive());
                    } else {
                        List<DayReport> dayReportList = new ArrayList<>();
                        DayReport report = convertIntoReport(row);
                        dayReportList.add(report);
                        stateData.setConfirmed(report.getConfirmed());
                        stateData.setRecovered(report.getRecovered());
                        stateData.setDeaths(report.getDeaths());
                        stateData.setActive(report.getActive());
                        stateData.setDayReports(dayReportList);
                        stateData.setName(row.getNameOfStateUt());
                        states.add(stateData);
                    }
                    stateDataRepository.save(stateData);
                    totalConfirmed += stateData.getConfirmed();
                    totalRecovered += stateData.getRecovered();
                    totalDeaths += stateData.getDeaths();
                    totalActive += stateData.getActive();
                }

                countryData.setConfirmed(totalConfirmed);
                countryData.setRecovered(totalRecovered);
                countryData.setDeaths(totalDeaths);
                countryData.setActive(totalActive);
                countryData.setStates(states);
                logger.info("Total Confirmed : " + totalConfirmed + " Total Recovered : " + totalRecovered + " Total Deaths : " + totalDeaths);

                countryDataRepository.save(fetchDistrictsData(countryData));
            }
            else{
                logger.info(" ------------- *********** GOVERNMENT API IS NOT WORKING. CHANGE THE API ASAP *********** ---------------- at " + LocalTime.now());
                logger.info(" ------------- *********** GOVERNMENT API IS NOT WORKING. CHANGE THE API ASAP *********** ---------------- at " + LocalTime.now());
                logger.info(" ------------- *********** GOVERNMENT API IS NOT WORKING. CHANGE THE API ASAP *********** ---------------- at " + LocalTime.now());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public CountryData fetchDistrictsData(CountryData countryData) {
        try {
            URL url = new URL(propertiesConfiguration.fetchDistrictStatsUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            StringBuilder data = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String currentLine = "";
            while ((currentLine = br.readLine())!=null){
                data = data.append(currentLine);
            }

            logger.info("\n Printing received districts data from External API : " + data.toString()+"\n");

            Gson gson = new Gson();
            ExtDataWrapper extDataWrapper = gson.fromJson("{ \"states\" : " + data.toString() + "}", ExtDataWrapper.class);
            if (countryData.getName().isEmpty() || (countryData.getName() == null)){
                countryData = countryDataRepository.findById("India").get();
            }
            List<StateData> states = countryData.getStates();
            List<District> districts = new ArrayList<>();

            // Printing the created object
            logger.info("Printing the converted object " + extDataWrapper.toString()+"\n");
            for (ExtState extState : extDataWrapper.getStates()){
                for (StateData state: states){
                    if (state.getName().equalsIgnoreCase(extState.getName())){
                        List<District> districtsForState = new ArrayList<>();
                        for (ExtDistrict extDistrict : extState.getDistricts()){
                            District district = new District();
                            district.setName(extDistrict.getName());
                            district.setConfirmed(extDistrict.getConfirmed());
                            districtsForState.add(district);
                            districts.add(district);
                        }
                        state.setDistricts(districtsForState);
                    }
                }
            }
            districtRepository.saveAll(districts);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return countryData;
    }

    @Override
    public void fetchHotSpots() {
        try {
            URL url = new URL(propertiesConfiguration.fetchHotSpotsUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            StringBuilder data = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String currentLine = "";
            while ((currentLine = br.readLine())!=null){
                data = data.append(currentLine);
            }

            logger.info("\n Printing the fetched HotSpot Data : " + data.toString()+"\n");

            Gson gson = new Gson();
            HotSpotWrapper hotSpotWrapper = gson.fromJson(data.toString(), HotSpotWrapper.class);

            // Printing the created object
            logger.info(hotSpotWrapper.toString()+"\n");
            for (HotSpot hotSpot : hotSpotWrapper.getHotSpots()){
                Double latitude = null;
                Double longitude = null;
                if (hotSpot.getLatLong().length() > 0){
                    String latLong = hotSpot.getLatLong();
                    String[] words = latLong.split(",");
                    if (words[0]!=null)
                    latitude = Double.parseDouble(words[0].trim());
                    if (words[1]!=null)
                    longitude = Double.parseDouble(words[1].trim());
                }
                hotSpot.setLatitude(latitude);
                hotSpot.setLongitude(longitude);
//                logger.info(hotSpot);
                hotSpotRepository.save(hotSpot);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertStatsData(){
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(propertiesConfiguration.dataFileLocation);
            Stats stats = gson.fromJson(JsonParser.parseReader(fileReader),Stats.class);
            CountryData countryData = new CountryData();
            List<StateData> states = new ArrayList<>();
            Map<String, StateData> statesMap = new HashMap<>();

            // Printing the created object
            logger.info("Printing the raw json object :" + stats.toString()+"\n");
            for (StatsRow row : stats.getRows()){
                StateData stateData = new StateData();
                List<DayReport> dayReportList = new ArrayList<>();
                DayReport report = new DayReport();
                if (statesMap.containsKey(row.getProperties().get("state"))) {
                    stateData = statesMap.get(row.getProperties().get("state"));
                    dayReportList=  stateData.getDayReports();
                }
                String[] dateSplitter = row.getId().split("T");
                String inputDate = dateSplitter[0];
                report.setDate(LocalDate.parse(inputDate));
                report.setConfirmed(Integer.parseInt(row.getProperties().get("confirmed")));
                report.setRecovered(Integer.parseInt(row.getProperties().get("cured")));
                report.setDeaths(Integer.parseInt(row.getProperties().get("death")));
                report.setActive(report.getConfirmed() - (report.getRecovered() + report.getDeaths()));
                if (dayReportList.size()>0){
                    if (report.getDate().toString().equalsIgnoreCase(dayReportList.get(dayReportList.size()-1).getDate().toString())){
                        dayReportList.remove(dayReportList.size()-1);
                    }
                }
                dayReportList.add(report);
                stateData.setName(row.getProperties().get("state"));
                stateData.setConfirmed(report.getConfirmed());
                stateData.setRecovered(report.getRecovered());
                stateData.setDeaths(report.getDeaths());
                stateData.setActive(report.getActive());
                stateData.setDayReports(dayReportList);
                statesMap.put(row.getProperties().get("state"),stateData);
            }

            int totalConfirmed = 0;
            int totalRecovered = 0;
            int totalDeaths = 0;
            int totalActive = 0;
            for (Map.Entry<String, StateData> state : statesMap.entrySet()){
                StateData stateData = state.getValue();
                String name = convertStateName(stateData.getName());
                stateData.setName(name);
                stateDataRepository.save(stateData);
                states.add(stateData);
                totalConfirmed += stateData.getConfirmed();
                totalRecovered += stateData.getRecovered();
                totalDeaths += stateData.getDeaths();
                totalActive += stateData.getActive();
            }
            countryData.setName("India");
            countryData.setConfirmed(totalConfirmed);
            countryData.setRecovered(totalRecovered);
            countryData.setDeaths(totalDeaths);
            countryData.setActive(totalActive);
            countryData.setStates(states);
            countryDataRepository.save(countryData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveStatsData(CountryData countryData) {
        List<StateData> states = countryData.getStates();
        List<District> districts = new ArrayList<>();
        for (StateData state : states){
            for (District district : state.getDistricts()){
                districts.add(district);
            }
        }

        districtRepository.saveAll(districts);
        stateDataRepository.saveAll(states);
        countryDataRepository.save(countryData);
    }

    DayReport convertIntoReport(Record row) throws Exception{
        DayReport dayReport = new DayReport();
        dayReport.setDate(LocalDate.parse(row.getDate()));
        dayReport.setConfirmed(Integer.parseInt(row.getTotalConfirmedCases()));
        dayReport.setRecovered(Integer.parseInt(row.getCuredDischargedMigrated()));
        dayReport.setDeaths(Integer.parseInt(row.getDeath()));
        dayReport.setActive(dayReport.getConfirmed() - (dayReport.getRecovered() + dayReport.getDeaths()));
        return dayReport;
    }

    String convertStateName(String name) {
        if (name.equalsIgnoreCase("an")) {
            return new String("Andaman and Nicobar Islands");
        } else if (name.equalsIgnoreCase("ap")) {
            return new String("Andhra Pradesh");
        } else if (name.equalsIgnoreCase("ar")) {
            return new String("Arunachal Pradesh");
        } else if (name.equalsIgnoreCase("as")) {
            return new String("Assam");
        } else if (name.equalsIgnoreCase("br")) {
            return new String("Bihar");
        } else if (name.equalsIgnoreCase("ch")) {
            return new String("Chandigarh");
        } else if (name.equalsIgnoreCase("ct")) {
            return new String("Chhattisgarh");
        } else if (name.equalsIgnoreCase("dl")) {
            return new String("Delhi");
        } else if (name.equalsIgnoreCase("ga")) {
            return new String("Goa");
        } else if (name.equalsIgnoreCase("gj")) {
            return new String("Gujarat");
        } else if (name.equalsIgnoreCase("hr")) {
            return new String("Haryana");
        } else if (name.equalsIgnoreCase("hp")) {
            return new String("Himachal Pradesh");
        } else if (name.equalsIgnoreCase("jk")) {
            return new String("Jammu and Kashmir");
        } else if (name.equalsIgnoreCase("jh")) {
            return new String("Jharkhand");
        } else if (name.equalsIgnoreCase("ka")) {
            return new String("Karnataka");
        } else if (name.equalsIgnoreCase("kl")) {
            return new String("Kerala");
        } else if (name.equalsIgnoreCase("la")) {
            return new String("Ladakh");
        } else if (name.equalsIgnoreCase("mh")) {
            return new String("Maharashtra");
        } else if (name.equalsIgnoreCase("mn")) {
            return new String("Manipur");
        } else if (name.equalsIgnoreCase("mp")) {
            return new String("Madhya Pradesh");
        } else if (name.equalsIgnoreCase("mz")) {
            return new String("Mizoram");
        } else if (name.equalsIgnoreCase("nl")) {
            return new String("Nagaland");
        } else if (name.equalsIgnoreCase("or")) {
            return new String("Odisha");
        } else if (name.equalsIgnoreCase("pb")) {
            return new String("Punjab");
        } else if (name.equalsIgnoreCase("py")) {
            return new String("Puducherry");
        } else if (name.equalsIgnoreCase("rj")) {
            return new String("Rajasthan");
        } else if (name.equalsIgnoreCase("sk")) {
            return new String("Sikkim");
        } else if (name.equalsIgnoreCase("tg")) {
            return new String("Telengana");
        } else if (name.equalsIgnoreCase("tn")) {
            return new String("Tamil Nadu");
        } else if (name.equalsIgnoreCase("tr")) {
            return new String("Tripura");
        } else if (name.equalsIgnoreCase("ut")) {
            return new String("Uttarakhand");
        } else if (name.equalsIgnoreCase("up")) {
            return new String("Uttar Pradesh");
        } else if (name.equalsIgnoreCase("wb")) {
            return new String("West Bengal");
        }
        return "";
    }

    @Override
    public CountryData getStatsData() throws Exception {
        ViewCount viewCount = new ViewCount();
        try{
            if (viewCountRepository.findById("pageHits").get() != null){
                viewCount = viewCountRepository.findById("pageHits").get();
                viewCount.setCount(viewCount.getCount() + 1);
            }else{
                viewCount.setName("pageHits");
                viewCount.setCount(1);
            }
        }catch (Exception e){
            viewCount.setName("pageHits");
            viewCount.setCount(1);
        }
        viewCountRepository.save(viewCount);
        return countryDataRepository.findById("India").get();
    }

    @Override
    public List<StateData> getStatesData() throws Exception {
        return stateDataRepository.findAll();
    }

    @Override
    public List<District> getDistrictsData() throws Exception {
        return districtRepository.findAll();
    }

    @Override
    public HotSpotWrapper getHotSpots() throws Exception {
        List<HotSpot> hotSpots = hotSpotRepository.findAll();
        HotSpotWrapper hotSpotWrapper = new HotSpotWrapper();
        hotSpotWrapper.setHotSpots(hotSpots);
        return hotSpotWrapper;
    }

    @Override
    public ViewCount getViews() {
        return viewCountRepository.findById("pageHits").get();
    }
}