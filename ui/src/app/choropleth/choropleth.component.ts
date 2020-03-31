import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service'; 
import {CurrentLocation} from '../current-location';
import * as d3 from 'd3';
import * as topojson from 'topojson';

@Component({
  selector: 'app-choropleth',
  templateUrl: './choropleth.component.html',
  styleUrls: ['./choropleth.component.css']
})
export class ChoroplethComponent implements OnInit {

  constructor(private service : AppService) { }
  inputData : any;
  currLocation = new CurrentLocation;
  startPos : any;


  ngOnInit() {
    this.service.getHotSpots().subscribe(data => {
      console.log(data);
      this.inputData = data;
    })
    console.log(this.inputData);

    var statsData;
    this.service.getStats().subscribe(data => {
      console.log(data);
      statsData = data;
    })
    console.log(statsData);

    var width = 500;
    var height = 500;

    var projection = d3.geoMercator()
                       .center([60,25])
                       .scale(800)
                       .translate([width / 10, height / 2])
       
    var path = d3.geoPath().projection(projection);

    var svg = d3.select("svg")
                .attr("width", width)
                .attr("height", height);
    
    var touchedPoint = null;

    function renderStatsMax(){
      var maxSate = statsData.data.regional[0];
      statsData.data.regional.forEach(state => {
        if (state.confirmedCasesIndian > maxSate.confirmedCasesIndian){
          maxSate = state;
        }
      });
      d3.select('#confirmed').text(maxSate.confirmedCasesIndian);
      d3.select('#deaths').text(maxSate.deaths);
      d3.select('#recovered').text(maxSate.discharged);
    }

    d3.json("https://raw.githubusercontent.com/geohacker/india/master/state/india_telengana.geojson").then(india => {
      
    
        console.log(india)
        console.log(topojson.feature(india, india));
        svg.append("g").selectAll('path')
            .data(india.features)
            .enter()
            .append('path')
            .attr("d", function(abc){return path(abc);})
            .style('fill', 'grey')
            .style('stroke', 'red')
            .style('stroke-width','.5px')
            .style('opacity','0.5')
            .on('mouseover', function(d){
              d3.select(this).style('fill','black').attr('cursor','pointer');
              d3.select('#description').text(d.properties.NAME_1);
              renderStats(d);
            })
            .on('mouseleave', function(){
              d3.select(this).style('fill','grey');
              renderStatsMax();
            })
        
        function renderStats(d){
          console.log(statsData);
          statsData.data.regional.forEach(state => {
            if (state.loc == d.properties.NAME_1){
              d3.select('#confirmed').text(state.confirmedCasesIndian);
              d3.select('#deaths').text(state.deaths);
              d3.select('#recovered').text(state.discharged);
            }
          });
        }

        // svg.append('path')
        //    .datum(india)
        //    .style('fill', 'grey')
        //    .style('opacity','0.3')
        //    .style('stroke', 'red')
        //    .style('stroke-width','.5px')
        //    .attr("d", path);
         
        svg.select('g').selectAll("circle")
        .data(this.inputData.hotSpots)
        .enter()
        .append("circle")
        .attr("cx", function(d) {return projection([d.longitude, d.latitude])[0];})
        .attr("cy", function(d) {return projection([d.longitude, d.latitude])[1];})
        .attr("r", "0.5px")
        .attr('fill','blue')
        .attr('d',path)
        .on('mouseover', function(d){

            d3.select(this).style('fill','black').attr('r','0.8px');
            d3.select('#id').text(d.id);
            d3.select('#description').text(d.description);
            d3.select('#patientID').text(d.patientID);
            d3.select('#startTime').text(d.startTime);
            d3.select('#endTime').text(d.endTime);
            d3.select('#type').text(d.type);
        })
        .on('mouseleave', function(d){
          d3.select(this).style('fill','blue').attr('r','0.5px');
          if (touchedPoint == d) touchedPoint = null;
        })
        .on('touchstart', (d) => {
          if (touchedPoint == d) touchedPoint = null;
          else touchedPoint = d;
        })
        .on('click', (d) => {
          if (touchedPoint) { return; }
          });
      
        function loc(position) {
          svg.select('g')
          .append("circle")
          .attr("cx", function() {return projection([position.coords.longitude, position.coords.latitude])[0];})
          .attr("cy", function() {return projection([position.coords.longitude, position.coords.latitude])[1];})
          .attr("r", "0.5px")
          .attr('fill','green')
          .attr('d',path);
        };
        navigator.geolocation.getCurrentPosition(loc);


      // var zoom = d3.zoom()
      //   .on("zoom", function() {
      //     svg.append('g').attr("transform", "translate(" + d3.event.center   + ")");
      //     svg.append('g').selectAll("path")
      //       .attr("d", path);
      //   });
      // svg.call(zoom);    
      svg.call(d3.zoom()
      .extent([[0, 0], [width, height]])
      .scaleExtent([1, 8])
      .on("zoom", zoomed));
      function zoomed() {
        svg.select('g').attr("transform", d3.event.transform);
      }    
    }); 
  }

}
