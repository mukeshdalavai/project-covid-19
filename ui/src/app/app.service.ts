import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http : HttpClient) { }

  getHotSpots(){
    const url = `http://13.235.78.95:8080/api/v1/get-hotspots`;
    console.log("Fetching HotSpots");
    return this.http.get<any>(url);
  }

  getStats(){
    const url = `https://api.rootnet.in/covid19-in/stats/latest`;
    console.group("Fetching Stats");
    return this.http.get<any>(url);
  }
}
