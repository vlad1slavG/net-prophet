import {Component, Renderer2, ViewChild} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow, MatRowDef, MatTable, MatTableDataSource
} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

export interface Host {
  id: string,
  hostName: string,
  hostIp: string,
  lastActiveDateTime: string
}

const DATA: Host[] = [
  { id: 'asdlkfjaswoj23455', hostName: 'DESKTOP-239487', hostIp: '185.36.123.9', lastActiveDateTime: 'some data'}
];

@Component({
  selector: 'app-hosts',
  standalone: true,
  imports: [
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatPaginator,
    MatRow,
    MatRowDef,
    MatTable,
    MatHeaderCellDef
  ],
  templateUrl: './hosts.component.html',
  styleUrl: './hosts.component.css'
})
export class HostsComponent {
  displayedColumns = ['id', 'hostName', 'hostIp', 'lastActiveDateTime'];
  dataSource: MatTableDataSource<Host>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private renderer: Renderer2) {
    this.dataSource = new MatTableDataSource(DATA);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
}
