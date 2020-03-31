import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service'; 
import {CurrentLocation} from '../current-location';
import * as d3 from 'd3';
import * as topojson from 'topojson';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() { }
  ngOnInit() {          
  }
}
