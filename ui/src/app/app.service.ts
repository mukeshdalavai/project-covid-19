import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http : HttpClient) { }

  getHotSpots(){
    const url = `http://3.6.88.34:8080/api/v1/get-hotspots`;
    console.log("Fetching HotSpots");
    return this.http.get<any>(url);
  }
}
