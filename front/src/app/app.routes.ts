import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then(c => c.DashboardComponent)
  },
  {
    path: 'hosts',
    loadComponent: () =>
      import('./pages/hosts/hosts.component').then(c => c.HostsComponent)
  },
  {
    path: 'flows',
    loadComponent: () =>
      import('./pages/flows/flows.component').then(c => c.FlowsComponent)
  },
  {
    path: 'alerts',
    loadComponent: () =>
      import('./pages/alerts/alerts.component').then(c => c.AlertsComponent)
  },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: 'dashboard', pathMatch: 'full' }
];
