import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CommercialDataComponent } from './commercial_data/commercial_data.component';
import { CommercialDataService } from './commercial_data/commercial_data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'my-first-project';
}
