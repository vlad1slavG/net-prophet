import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-dash-tabs',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './dash-tabs.component.html',
  styleUrl: './dash-tabs.component.css'
})
export class DashTabsComponent {
  alerts = 23;
  hosts = 4;
  flows = 123;
}
