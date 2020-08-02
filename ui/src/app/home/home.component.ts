import { Component,HostListener, OnInit } from '@angular/core';
import { AppService } from '../app.service'; 
import {CurrentLocation} from '../current-location';
import * as d3 from 'd3';
import * as topojson from 'topojson';

declare const CanvasJS : any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html' ,
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

 
  constructor(private service : AppService) { }
  stats : any;
  
  hoveredState : any;

  dataPointsConfirmed = [];
  dataPointsActive = [];
  dataPointsRecovered = [];
  dataPointsDeaths = [];
  pieChartPoints = [];
  oldScreenWidth : any;
  

  isMobile : boolean = true;
  isShow: boolean;
  topPosToStartShowing = 100;

  @HostListener('window:scroll')
  checkScroll() {

    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    
    if (scrollPosition >= this.topPosToStartShowing) {
      this.isShow = true;
    } else {
      this.isShow = false;
    }
  }
  
  gotoTop() {
    window.scroll({ 
      top: 0, 
      left: 0, 
      behavior: 'smooth' 
    });
  }

  ngOnInit() { 
    this.oldScreenWidth = screen.width;  

    // console.log(screen.width);
    if (screen.width > 760){
      this.isMobile = false;
    }
    if (screen.width <= 760){
      this.isMobile = true;
    }
    // console.log(this.isMobile);

    this.service.fetchStats().subscribe(data =>{
      // console.log(data);
      this.stats = data;
      d3.select("#totalConfirmed").text(this.stats.confirmed);
      d3.select("#totalActive").text(this.stats.active);
      d3.select("#totalDeaths").text(this.stats.deaths);
      d3.select("#totalRecovered").text(this.stats.recovered);

      if (screen.width > 760 && screen.width <950){
        d3.select("#textConfirmed").text("Cnfmd");
        d3.select("#textActive").text("Actv");
        d3.select("#textDeaths").text("Dths");
        d3.select("#textRecovered").text("Rcvrd");
      }
      else{
        d3.select("#textConfirmed").text("Confirmed");
        d3.select("#textActive").text("Active");
        d3.select("#textDeaths").text("Deaths");
        d3.select("#textRecovered").text("Recovered");
      }

      this.renderStatsMax();
    });
  }

  receiveMessage($event) {
    // console.log($event);
    this.dataPointsConfirmed.length = 0;
    this.dataPointsActive.length = 0;
    this.dataPointsRecovered.length = 0;
    this.dataPointsDeaths.length = 0;
    this.pieChartPoints.length = 0;
    this.hoveredState = $event;

    this.stats.states.forEach(state => {
      if (state.name == this.hoveredState){
        state.dayReports.forEach(dayRecord => {
            this.dataPointsConfirmed
              .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.confirmed)});
            this.dataPointsActive
              .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.active)});
            this.dataPointsRecovered
              .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.recovered)});
            this.dataPointsDeaths
              .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.deaths)});
        });

      this.pieChartPoints.push({ y: state.active, label: "Active", color: "#71b0e8"});
      this.pieChartPoints.push({ y: state.recovered, label: "Recovered", color: "#82d386"});
      this.pieChartPoints.push({ y: state.deaths, label: "Deaths", color: "#f77b7b"});

      d3.select('#state').text(state.name);
      d3.select('#confirmed').text(state.confirmed);
      d3.select('#active').text(state.active);
      d3.select('#deaths').text(state.deaths);
      d3.select('#recovered').text(state.recovered);
      }
    });
    this.renderData();
  }

  renderStatsMax(){
    var maxState = this.stats.states[0];
    this.stats.states.forEach(state => {
      if (state.confirmed > maxState.confirmed){
        maxState = state;
      }
    });
    d3.select('#state').text(maxState.name);
    d3.select('#confirmed').text(maxState.confirmed);
    d3.select('#active').text(maxState.active);
    d3.select('#deaths').text(maxState.deaths);
    d3.select('#recovered').text(maxState.recovered);

    d3.select('#districtName').text(maxState.districts[0].name);
    d3.select('#districtConfirmed').text(maxState.districts[0].confirmed);

    maxState.dayReports.forEach(dayRecord => {
      this.dataPointsConfirmed
        .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.confirmed)});
      this.dataPointsActive
        .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.active)});
      this.dataPointsRecovered
        .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.recovered)});
      this.dataPointsDeaths
        .push({x : new Date(dayRecord.date), y : parseInt(dayRecord.deaths)});
    });

    this.pieChartPoints.push({ y: maxState.active, label: "Active", color: "#71b0e8"});
    this.pieChartPoints.push({ y: maxState.recovered, label: "Recovered", color: "#82d386"});
    this.pieChartPoints.push({ y: maxState.deaths, label: "Deaths", color: "#f77b7b"});

    this.renderData();
  }


  renderData(){
    let chart = new CanvasJS.Chart("lineChart",{
      theme: "dark",
      animationEnabled: true,
      exportEnabled: true,
      axisX: {
        valueFormatString: "DD MMM"
      },
      axisY: {
        title: "Number of Cases"
      },
      toolTip: {
        shared: true
      },
      legend: {
        cursor: "pointer",
        verticalAlign: "top",
        horizontalAlign: "center",
        dockInsidePlotArea: false,
        itemclick: function(e){
          if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
            e.dataSeries.visible = false;
          } else{
            e.dataSeries.visible = true;
          }
        }
      },
      data: [{
        type: "line",
        axisYType: "primary",
        name: "Confirmed",
        showInLegend: true,
        color: "#fccf4a",
        dataPoints: this.dataPointsConfirmed
      },
      {
        type: "line",
        axisYType: "primary",
        name: "Active",
        showInLegend: true,
        color: "#71b0e8",
        dataPoints: this.dataPointsActive
      },
      {
        type: "line",
        axisYType: "primary",
        name: "Deaths",
        showInLegend: true,
        color: "#f77b7b",
        dataPoints: this.dataPointsDeaths
      },
      {
        type: "line",
        axisYType: "primary",
        name: "Recovered",
        showInLegend: true,
        color: "#82d386",
        dataPoints: this.dataPointsRecovered
      }
    ]
    })
    chart.render();

    var pieChart = new CanvasJS.Chart("pieChart", {
      animationEnabled: true,
      exportEnabled: true,
      theme: "dark",
      // backgroundColor: "cornsilk",
      data: [{
        type: "doughnut",
        startAngle: 60,
        //innerRadius: 60,
        indexLabelFontSize: 17,
        indexLabel: "{label} - #percent%",
        toolTipContent: "<b>{label}:</b> {y} (#percent%)",
        dataPoints: this.pieChartPoints
      }]
    });
    pieChart.render();
  }

  onResize($event){
    if (this.oldScreenWidth > 760 && $event.target.innerWidth <= 760){
      console.log("Lappy to mobile");
      window.location.reload();
    }
    if (this.oldScreenWidth < 760 && $event.target.innerWidth >= 760){
      console.log("mobile to lappy");
      window.location.reload();
    }
    // window.location.reload();
    // console.log($event);
  }
}

