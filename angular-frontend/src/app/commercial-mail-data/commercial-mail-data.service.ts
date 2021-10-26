import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommercialMailData } from './commercial-mail-data';

@Injectable({
  providedIn: 'root'
})
export class CommercialMailDataService {

  private baseURL = "http://localhost:8080/api/commercial_mail_data";

  private temp123!: CommercialMailData;

  constructor(private http: HttpClient) { }

  getCommercialMailData(): Observable<CommercialMailData[]>{
    return this.http.get<CommercialMailData[]>(this.baseURL);
  }
/*
  get(id: number): Observable<CommercialMailData>{
    console.log(this.baseURL + "/" + id+1);
    console.log(this.http.get<CommercialMailData>(this.baseURL + "/" + id+1));
    return this.http.get<CommercialMailData>(this.baseURL + "/" + id+1);
  }
  */
  getCommercialMailDataByID(idCom: String): Observable<CommercialMailData>{
    
    let temp = this.http.get<CommercialMailData[]>(this.baseURL);
    let returnValue!: CommercialMailData;
    //temp.

    temp.forEach( (value) => {
      value.filter((commercialMailD) => {
        if(commercialMailD.idCommercial == idCom){
          returnValue = commercialMailD;//.idCommercial;
          this.temp123 = returnValue;
        }

      });
    });

    console.log(returnValue)
    

    return this.http.get<CommercialMailData>(this.baseURL + "/" + returnValue);
  }


  

  /*
  get(idCommercial: String): Observable<any>{
    console.log(this.baseURL + "/" + idCommercial);
    console.log(this.http.get<CommercialMailData>(this.baseURL + "/" + idCommercial));
    return this.http.get<CommercialMailData>(this.baseURL + "/" + idCommercial);
  }
*/

  



}
