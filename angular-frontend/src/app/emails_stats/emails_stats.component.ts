import { Component, OnInit } from '@angular/core';
import { EmailsStats } from './emails_stats';
import { EmailsStatsService } from './emails_stats.service';

@Component({
  selector: 'app-emails-stats',
  templateUrl: './emails_stats.component.html',
  styleUrls: ['./emails_stats.component.css']
})
export class EmailsStatsComponent implements OnInit {

  emails_stats!: EmailsStats[];
  constructor(private emailStatsService: EmailsStatsService) { }

  ngOnInit(): void {
    this.emailStatsService.getEmailsStats().subscribe((data: EmailsStats[]) => {
      console.log(data[0]);
      this.emails_stats = data;
    });
  }

}
