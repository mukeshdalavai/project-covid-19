import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http : HttpClient) { }

  // getHotSpots(){
  //   const url = `http://52.66.213.210:8080/api/v1/get-hotspots`;
  //   console.log("Fetching HotSpots");
  //   return this.http.get<any>(url);
  // }


  // getGovStatesData(){
  //   const url = `https://api.data.gov.in/resource/cd08e47b-bd70-4efb-8ebc-589344934531?limit=all&api-key=579b464db66ec23bdd000001cdc3b564546246a772a26393094f5645&format=json`;
  //   console.log("Fetching Government Data for States");
  //   return this.http.get<any>(url);
  // }

  fetchStats(){
    const url = `https://covidmapper.in/api/v1/data`;
    // console.log("Fetching stats data");
    return this.http.get<any>(url);
  }

  fetchDistrictsData(){
    const url = `https://covidmapper.in/api/v1/district-data`;
    // console.log("Fetching Districts Data");
    return this.http.get<any>(url);
  }
}
