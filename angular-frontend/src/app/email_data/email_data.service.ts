import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmailData } from './email_data';

@Injectable({
  providedIn: 'root'
})
export class EmailDataService {

  private baseURL = "http://localhost:8080/api/emails";

  constructor(private http: HttpClient) { }

  getEmailsData(): Observable<EmailData[]>{
    return this.http.get<EmailData[]>(this.baseURL);
  }

  getEmailsDataByIdCommercial(idCommercial: String): Observable<EmailData[]>{
    return this.http.get<EmailData[]>(this.baseURL + "/" + idCommercial);
  }

  getEmailsDataIsReceiver(idCommercial: String): Observable<EmailData[]>{
    return this.http.get<EmailData[]>(this.baseURL + "/is_receiver/" + idCommercial);
  }
  getEmailsDataIsSender(idCommercial: String): Observable<EmailData[]>{
    return this.http.get<EmailData[]>(this.baseURL + "/is_sender/" + idCommercial);
  }
}
