import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmailsStats } from './emails_stats';

@Injectable({
  providedIn: 'root'
})
export class EmailsStatsService {

  private baseURL = "http://localhost:8080/api/emails_stats";

  constructor(private http: HttpClient) { }

  getEmailsStats(): Observable<EmailsStats[]>{
    return this.http.get<EmailsStats[]>(this.baseURL);
  }
}
