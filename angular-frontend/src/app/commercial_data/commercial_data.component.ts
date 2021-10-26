import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { CommercialMailData } from '../commercial-mail-data/commercial-mail-data';
import { CommercialMailDataService } from '../commercial-mail-data/commercial-mail-data.service';
import { EmailDataService } from '../email_data/email_data.service';
import { CommercialData } from './commercial_data';
import { CommercialDataService } from './commercial_data.service';


@Component({
  selector: 'app-commercial-data',
  templateUrl: './commercial_data.component.html',
  styleUrls: ['./commercial_data.component.css']
})
export class CommercialDataComponent implements OnInit {

  commercials_data!: CommercialData[];
  selectedCommercialData!: CommercialData;
  //httpClient!: HttpClient;

  constructor(private httpClient: HttpClient, private commercialDataService: CommercialDataService, private commercialMailDataService: CommercialMailDataService, private route: Router) { }


  onDetails(id: number, idCommercial: String){
    
    var idNumber: String = id.toString();
    //console.log(this.commercial_mail_data_datePicker[0]);//commercial_mail_data
    let editURL = 'http://localhost:8080/api/commercial_mail_data/refresh_mails_data/' + idNumber;
    this.commercialDataService.get(idNumber).subscribe((data: CommercialData) => {
      

      this.httpClient.put(editURL, data)
        .subscribe(() => {

          editURL = 'http://localhost:8080/api/emails/refresh/' + idCommercial;
          this.httpClient.put(editURL, null)
            .subscribe(() => {
              this.ngOnInit();
            });
          
        });
        
    });

    



    
    const newUrl = '../commercial_mail_data/' + idCommercial;
    this.route.navigate([newUrl])
    
  }


  setCommercialDelegated(id:number) {
    const editURL = 'http://localhost:8080/api/commercial_data/' + id + '/edit';

    //var idNumber:number = id;
    var idNumber: String = id.toString();
    this.commercialDataService.get(idNumber).subscribe((data: CommercialData) => {
      this.selectedCommercialData = data;

      this.selectedCommercialData.isDelegated = true;
      this.httpClient.put(editURL, this.selectedCommercialData)
        .subscribe(() => {
          this.ngOnInit();
        });
    });
  }
  removeDelegationCommercial(id: number) {

    var idNumber: String = id.toString();


    const editURL = 'http://localhost:8080/api/commercial_data/' + id + '/edit';
    this.commercialDataService.get(idNumber).subscribe((data: CommercialData) => {
      this.selectedCommercialData = data;

      this.selectedCommercialData.isDelegated = false;
      this.httpClient.put(editURL, this.selectedCommercialData)
        .subscribe(() => {
          this.ngOnInit();
        });
    });
  }

  onHome(){
    
    const editURL = 'http://localhost:8080/api/commercial_data/refresh';
      
      this.httpClient.put(editURL, null)
        .subscribe(() => {
          this.ngOnInit();
        });
        const newUrl = '../commercials';
        this.route.navigate([newUrl])
  }

  ngOnInit(): void {


      
 

    this.commercialDataService.getCommercialData().subscribe((data: CommercialData[]) => {
      this.commercials_data = data;
    });

  }

}
