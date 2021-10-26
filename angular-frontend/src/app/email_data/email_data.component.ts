import { Component, OnInit } from '@angular/core';
import { EmailData } from './email_data';
import { EmailDataService } from './email_data.service';

@Component({
  selector: 'app-email',
  templateUrl: './email_data.component.html',
  styleUrls: ['./email_data.component.css']
})
export class EmailDataComponent implements OnInit {

  emails!: EmailData[];
  constructor(private emailDataService: EmailDataService) { }

  ngOnInit(): void {
    this.emailDataService.getEmailsData().subscribe((data: EmailData[]) => {
      console.log(data);
      this.emails = data;
    });
  }

}
