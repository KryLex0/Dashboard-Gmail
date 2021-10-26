import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommercialData } from './commercial_data';

@Injectable({
  providedIn: 'root'
})
export class CommercialDataService {

  private baseURL = "http://localhost:8080/api/commercial_data";

  constructor(private http: HttpClient) { }

  getCommercialData(): Observable<CommercialData[]>{
    return this.http.get<CommercialData[]>(this.baseURL);
  }

  get(id: String): Observable<CommercialData>{
    return this.http.get<CommercialData>(this.baseURL + "/" + id);
  }
/*
  getEmailSender(id: String): Observable<CommercialData>{
    return this.http.get<CommercialData>(this.baseURL + "/");
  }

  getEmailReceiver(id: String): Observable<CommercialData>{
    return this.http.get<CommercialData>(this.baseURL + "/");
  }
  */
/*
  getCommercialName(id: number): Observable<CommercialData>{
    return this.get(id);
  }
*/
/*
  isMailDelegated(commercialData: CommercialData): String {
    let str = ""
    if(commercialData.isDelegated == true){
      str = "Autorisé"
    }else{
      str = "NON AUTORISE"
    }
    //return this.authService.current_route === 'site/map'
    return str
  }
  */

}
