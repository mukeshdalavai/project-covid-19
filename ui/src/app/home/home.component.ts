import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service'; 
import * as d3 from 'd3';
import * as topojson from 'topojson';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private service : AppService) { }
  inputData : any;

  ngOnInit() {

    this.service.getHotSpots().subscribe(data => {
      console.log(data);
      this.inputData = data;
    })

    var width = 1000;
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

    d3.json("https://raw.githubusercontent.com/geohacker/india/master/state/india_telengana.geojson").then(india => {
        console.log(india)
        console.log(topojson.feature(india, india));
        // svg.append("g").selectAll('path')
        //     .data(world.features)
        //     .enter()
        //     .append('path')
        //     .attr("d", function(abc){return path(abc);})
        //     .style('fill', 'grey')
        //     .style('stroke', 'red')
        //     .style('stroke-width','.5px')
        //     .style('opacity','0.5');

        svg.append('path')
           .datum(india)
           .style('fill', 'grey')
           .style('opacity','0.3')
           .style('stroke', 'red')
           .style('stroke-width','.5px')
           .attr("d", path);
            
        svg.append('g').selectAll("circle")
        .data(this.inputData.hotSpots)
        .enter()
        .append("circle")
        .attr("cx", function(d) {return projection([d.longitude, d.latitude])[0];})
        .attr("cy", function(d) {return projection([d.longitude, d.latitude])[1];})
        .attr("r", "2px")
        .attr('fill','blue')
        .attr('d',path)
        .on('mouseover', function(d){

            d3.select(this).style('fill','black').attr('r','5px');
            d3.select('#id').text(d.id);
            d3.select('#description').text(d.description);
            d3.select('#patientID').text(d.patientID);
            d3.select('#startTime').text(d.startTime);
            d3.select('#endTime').text(d.endTime);
            d3.select('#type').text(d.type);
        })
        .on('mouseleave', function(d){
          d3.select(this).style('fill','blue').attr('r','2px');
          if (touchedPoint == d) touchedPoint = null;
        })
        .on('touchstart', (d) => {
          if (touchedPoint == d) touchedPoint = null;
          else touchedPoint = d;
        })
        .on('click', (d) => {
          if (touchedPoint) { return; }
          });
      
      var zoom = d3.zoom()
        .on("zoom", function() {
          svg.append('g').attr("transform", "translate(" + d3.event.scale + ")");
          svg.append('g').selectAll("path")
            .attr("d", path);
        });
      svg.call(zoom);        
    });

        


        // svg.append("path")
        //     .datum(topojson.mesh(world, world.objects.countries, (a, b) => a !== b))
        //     .style('fill', 'none')
        //     .style('stroke', '#fff')
        //     .style('stroke-width','.5px')
        //     .attr("class", "boundary")
        //     .attr("d", path);
        // svg.append("path")
        //     .datum(graticule)
        //     .attr("class", "graticule")
        //     .attr("d", path);
    // d3.select(self.frameElement).style("height", height + "px");

      
    
  }
}
