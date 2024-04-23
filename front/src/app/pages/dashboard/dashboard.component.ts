import { Component } from '@angular/core';
import {DashTabsComponent} from "../../components/dash-tabs/dash-tabs.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    DashTabsComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
