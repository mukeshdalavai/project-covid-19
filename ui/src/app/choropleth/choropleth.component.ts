import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AppService } from '../app.service'; 
import {CurrentLocation} from '../current-location';
import * as d3 from 'd3';
import * as topojson from 'topojson';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-choropleth',
  templateUrl: './choropleth.component.html',
  styleUrls: ['./choropleth.component.css']
})
export class ChoroplethComponent implements OnInit {

  @Output() pushToParent = new EventEmitter<String>();

  constructor(private service : AppService, private http : HttpClient) { }
  // inputData : any;
  currLocation = new CurrentLocation;
  startPos : any;
  isWait : boolean;

  sendMessage(d){
    this.pushToParent.emit(d.properties.NAME_1);
  }

  ngOnInit() {
    this.isWait = true;

    // this.service.getHotSpots().subscribe(data => {
    //   console.log(data);
    //   this.inputData = data;
    // })


    let style = window.getComputedStyle(document.querySelector("#map"));

    let width  = parseFloat(style.width.replace("px", ""));
    let height = parseFloat(style.height.replace("px", ""));

    var projection = d3.geoMercator()
                       .center([80,23])
                       .scale(800)
                       .translate([width / 2, height / 2])
       
    var path = d3.geoPath().projection(projection);

    var svg = d3.selectAll("#map")
                .attr("width", width)
                .attr("height", height);
    
    var touchedPoint = null;

    var indiaData;
    var abc = this;

    this.http.get("assets/topojson/Indian_States.json").subscribe(indiaJson => {
      this.isWait = false;
      indiaData = indiaJson;
      // console.log(indiaData);
      // console.log(topojson.feature(indiaData, indiaData.objects.Indian_States).features);
      svg.append("g").selectAll('path')
      .data(topojson.feature(indiaData, indiaData.objects.gadm36_IND_1).features)
      .enter()
      .append('path')
      .attr("d", function(abc){return path(abc);})
      .style('fill', '#8cea8e')
      .style('stroke', 'cornsilk')
      .style('stroke-width','1px')
      .style('opacity','0.5')
      .on('mouseover', function(d){
        d3.select(this).style('fill','#4c4b47').style('opacity','1.0').style('stroke-width','2px').attr('cursor','pointer');
        abc.sendMessage(d);
        appendDistricts(d);
      })
      .on('mouseleave', function(){
        d3.select(this).style('fill','#8cea8e').style('stroke-width','1px').style('opacity','0.5');
      });
      

      svg.call(d3.zoom()
      .extent([[0, 0], [width, height]])
      .scaleExtent([1, 8])
      .on("zoom", zoomed));

      function zoomed() {
        svg.select('g').attr("transform", d3.event.transform);
      }  
      
      d3.select('#zoom-in').on('click', function() {
        d3.zoom()
          .on('zoom', zoomed)
          .scaleBy(svg.transition().duration(750), 1.3); 
      });

      d3.select('#zoom-out').on('click', function() {
        d3.zoom()
          .on('zoom', zoomed)
          .scaleBy(svg.transition().duration(750), 1/1.3); 
      });
    });
    
    var districtData;
    this.http.get("assets/topojson/Indian_Districts.json").subscribe(data => {
      // console.log(data);
      districtData = data;
    });

    function appendDistricts(d){
      var districts = topojson.feature(districtData, districtData.objects.gadm36_IND_2).features;
      var hoveredStateDistricts = [];
      districts.forEach(district => {
        if (district.properties.NAME_1 == d.properties.NAME_1){
          hoveredStateDistricts.push(district);
        }
      });
      svg.select('g').select('g').remove();
      svg.select("g").append('g').selectAll('path')
          .data(hoveredStateDistricts)
          .enter()
          .append('path')
          .attr("d", function(abc){return path(abc);})
          .style('fill', 'red')
          .style('stroke', 'cornsilk')
          .style('stroke-width','1px')
          .style('opacity','0.5')
          .on('mouseover', function(d){
            d3.select(this).style('fill','#4c4b47').style('opacity','1.0').attr('cursor','pointer');
            renderDistrictInfo(d);
          })
          .on('mouseleave', function(){
            d3.select(this).style('fill','red').style('stroke-width','1px').style('opacity','0.5');
          });
    }

    var districtStats;
    this.service.fetchDistrictsData().subscribe(data => {
    // console.log(data);  
    districtStats = data;
    })

    function renderDistrictInfo(d){
      var isDistrictExist = false;
      districtStats.forEach( district => {
        d3.select("#districtName").text(d.properties.NAME_2);
        if (district.name == d.properties.NAME_2){
           d3.select("#districtConfirmed").text(district.confirmed);
           isDistrictExist = true;
        }
      });
      if (isDistrictExist == false){
        d3.select("#districtConfirmed").text("NA");

      }
    }

         
    //     svg.select('g').selectAll("circle")
    //         .data(this.inputData.hotSpots)
    //         .enter()
    //         .append("circle")
    //         .attr("cx", function(d) {return projection([d.longitude, d.latitude])[0];})
    //         .attr("cy", function(d) {return projection([d.longitude, d.latitude])[1];})
    //         .attr("r", "0.5px")
    //         .attr('fill','blue')
    //         .attr('d',path)
    //         .on('mouseover', function(d){

    //         d3.select(this).style('fill','black').attr('r','0.8px');
    //         d3.select('#id').text(d.id);
    //         d3.select('#description').text(d.description);
    //         d3.select('#patientID').text(d.patientID);
    //         d3.select('#startTime').text(d.startTime);
    //         d3.select('#endTime').text(d.endTime);
    //         d3.select('#type').text(d.type);
    //     })
    //     .on('mouseleave', function(d){
    //       d3.select(this).style('fill','blue').attr('r','0.5px');
    //       if (touchedPoint == d) touchedPoint = null;
    //     })
    //     .on('touchstart', (d) => {
    //       if (touchedPoint == d) touchedPoint = null;
    //       else touchedPoint = d;
    //     })
    //     .on('click', (d) => {
    //       if (touchedPoint) { return; }
    //       });
      
    //     function loc(position) {
    //       svg.select('g')
    //       .append("circle")
    //       .attr("cx", function() {return projection([position.coords.longitude, position.coords.latitude])[0];})
    //       .attr("cy", function() {return projection([position.coords.longitude, position.coords.latitude])[1];})
    //       .attr("r", "0.5px")
    //       .attr('fill','green')
    //       .attr('d',path);
    //     };
    //     navigator.geolocation.getCurrentPosition(loc);

  }

}
