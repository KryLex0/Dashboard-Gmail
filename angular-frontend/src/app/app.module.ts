import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { EmailDataComponent } from './email_data/email_data.component';
import { appRoutingModule } from './app.routing';
import { EmailsStatsComponent } from './emails_stats/emails_stats.component';
import { CommercialDataComponent } from './commercial_data/commercial_data.component';
import { CommercialMailDataComponent } from './commercial-mail-data/commercial-mail-data.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    EmailDataComponent,
    EmailsStatsComponent,
    CommercialDataComponent,
    CommercialMailDataComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    appRoutingModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
