import { Routes, RouterModule } from '@angular/router';

import { EmailDataComponent } from '../app/email_data/email_data.component';
import { EmailsStatsComponent } from '../app/emails_stats/emails_stats.component';
import { CommercialDataComponent } from '../app/commercial_data/commercial_data.component';
import { CommercialMailDataComponent } from './commercial-mail-data/commercial-mail-data.component';


const routes: Routes = [
    { path: 'email_data', component: EmailDataComponent },
    { path: 'commercials', component: CommercialDataComponent },

    { path: 'commercial_mail_data/:idCommercial', component: CommercialMailDataComponent },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const appRoutingModule = RouterModule.forRoot(routes);