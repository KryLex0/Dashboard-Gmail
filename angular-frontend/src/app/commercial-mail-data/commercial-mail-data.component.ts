import { Component, OnInit } from '@angular/core';
import { CommercialMailData } from './commercial-mail-data';
import { CommercialMailDataService } from './commercial-mail-data.service';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { CommercialDataService } from '../commercial_data/commercial_data.service';

import '../../assets/script.js';
import { HttpClient } from '@angular/common/http';
import { EmailDataService } from '../email_data/email_data.service';
import { EmailData } from '../email_data/email_data';
import { waitForAsync } from '@angular/core/testing';

declare const myFunc: any;
declare const onInitPage: any;



@Component({
  selector: 'app-commercial-mail-data',
  templateUrl: './commercial-mail-data.component.html',
  styleUrls: ['./commercial-mail-data.component.css']
})
export class CommercialMailDataComponent implements OnInit {
  temp!: CommercialMailData;
  id!: any;

  commercials_mail_data1: CommercialMailData[] = [];

  commercial_mail_data_datePicker: CommercialMailData[] = [];

  commercial_name!: String;
  commercial_email!: String;
  nbMailsReceivedDatePicker = 0;
  nbMailsSentDatePicker = 0;

  emailDataIsReceived: EmailData[] = [];
  emailDataIsSent: EmailData[] = [];

  emailsData: EmailData[] = [];


  commercials_mail_data!: CommercialMailData[];
  
  constructor(private emailDataService: EmailDataService, private httpClient: HttpClient, private commercialDataService: CommercialDataService, private commercialMailDataService: CommercialMailDataService, private activatedRoute: ActivatedRoute, public datepipe: DatePipe) { }



  getDateStartWeek(dateD:String, dateF:String){
    if(myFunc(dateD, dateF)){
      console.log(dateD + "----" + dateF);

      this.nbMailsReceivedDatePicker = 0;
      this.nbMailsSentDatePicker = 0;

      

      


      for(var i=0; i<this.commercials_mail_data1.length; i++){//this.commercials_mail_data1.forEach((commercial) =>{
        
        if(dateD >= String(this.commercials_mail_data1[i].dateStartWeek) && dateD <= String(this.commercials_mail_data1[i].dateEndWeek) && dateF >= String(this.commercials_mail_data1[i].dateEndWeek) ||
        dateD <= String(this.commercials_mail_data1[i].dateStartWeek) && dateF >= String(this.commercials_mail_data1[i].dateStartWeek) && dateF >= String(this.commercials_mail_data1[i].dateEndWeek) ||
        dateD >= String(this.commercials_mail_data1[i].dateStartWeek) && dateD <= String(this.commercials_mail_data1[i].dateEndWeek) && dateF <= String(this.commercials_mail_data1[i].dateEndWeek) ||
        dateD <= String(this.commercials_mail_data1[i].dateStartWeek) && dateF >= String(this.commercials_mail_data1[i].dateStartWeek) && dateF <= String(this.commercials_mail_data1[i].dateEndWeek)
        ){

          this.nbMailsReceivedDatePicker += this.commercials_mail_data1[i].nbMailsReceived;
          this.nbMailsSentDatePicker += this.commercials_mail_data1[i].nbMailsSent;

          this.commercial_mail_data_datePicker = [this.commercials_mail_data1[i]];

        }else{}
        
      }
    }else{
    }

  }

  ngOnInit(): void {
    onInitPage();

    this.activatedRoute.paramMap.subscribe(paramsId => {
      this.id = paramsId.get('idCommercial');

      
      this.emailDataService.getEmailsDataIsReceiver(this.id).subscribe((val)=>{
        for(var i=0; i<val.length; i++){
          this.emailDataIsReceived.push(val[i]);
        }
        
      });

      this.emailDataService.getEmailsDataIsSender(this.id).subscribe((val)=>{
        for(var i=0; i<val.length; i++){
          this.emailDataIsSent.push(val[i]);
        }
      });

      if(this.emailDataIsSent.length == 5 && this.emailDataIsReceived.length == 5){
        console.log("FINIT");
      }

      this.emailDataService.getEmailsDataByIdCommercial(this.id).subscribe((val)=>{
        for(var i=0; i<val.length; i++){
          this.emailsData.push(val[i]);
        }
      });

      this.commercialMailDataService.getCommercialMailData().subscribe((val) => {
        this.commercialDataService.getCommercialData().subscribe((val1) => {
          for(var i=0; i<val1.length; i++){
            if(val1[i].idCommercial == this.id){
              this.commercial_name = val1[i].nomCommercial;
              this.commercial_email = val1[i].emailCommercial;
            }
          }
        });
        for(let i=0; i<val.length; i++){
          if(val[i].idCommercial == this.id){
            this.commercials_mail_data1.push(val[i]);
          }
  
        }
        this.commercial_mail_data_datePicker = [this.commercials_mail_data1[this.commercials_mail_data1.length-1]];
        this.nbMailsReceivedDatePicker = this.commercials_mail_data1[this.commercials_mail_data1.length-1].nbMailsReceived;
        this.nbMailsSentDatePicker = this.commercials_mail_data1[this.commercials_mail_data1.length-1].nbMailsSent;

      });

  });

 


  }

}




