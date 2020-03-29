package com.covidMapper.service;
import com.covidMapper.config.PropertiesConfiguration;
import com.covidMapper.domain.HotSpot;
import com.covidMapper.domain.HotSpotWrapper;
import com.covidMapper.repository.HotSpotRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    HotSpotRepository hotSpotRepository;

    @Autowired
    PropertiesConfiguration propertiesConfiguration;

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

            System.out.println("\n" + data.toString()+"\n");

            Gson gson = new Gson();
            HotSpotWrapper hotSpotWrapper = gson.fromJson(data.toString(), HotSpotWrapper.class);

            // Printing the created object
            System.out.println(hotSpotWrapper.toString()+"\n");
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
                System.out.println(hotSpot);
                hotSpotRepository.save(hotSpot);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public HotSpotWrapper getHotSpots() throws Exception {
        List<HotSpot> hotSpots = hotSpotRepository.findAll();
        HotSpotWrapper hotSpotWrapper = new HotSpotWrapper();
        hotSpotWrapper.setHotSpots(hotSpots);
        return hotSpotWrapper;
    }
}