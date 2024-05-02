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

export interface NetworkFLow {
  srcIp: string,
  srcPort: number,
  destIp: string,
  destPort: number,
  protocol: number,
  packetsIn: number,
  bytesIn: number,
  packetsOut: number,
  bytesOut: number,
  duration: number
}

const DATA: NetworkFLow[] = [
  {
    srcIp: '40.114.177.156', srcPort: 443, destIp: '192.168.240.235', destPort: 58332,
    protocol: 6, packetsIn: 4, bytesIn: 279, packetsOut: 6, bytesOut: 423, duration: 0.0840
  },
  {
    srcIp: '192.168.240.235', srcPort: 58049, destIp: '185.199.109.153', destPort: 443,
    protocol: 6, packetsIn: 5, bytesIn: 306, packetsOut: 4, bytesOut: 279, duration: 0.0795
  },
  {
    srcIp: '192.168.240.235', srcPort: 58041, destIp: '34.107.141.31', destPort: 443,
    protocol: 6, packetsIn: 4, bytesIn: 279, packetsOut: 8, bytesOut: 519, duration: 0.1029
  },
  {
    srcIp: '192.168.240.235', srcPort: 58031, destIp: '104.76.220.193', destPort: 80,
    protocol: 6, packetsIn: 3, bytesIn: 186, packetsOut: 4, bytesOut: 218, duration: 14.9392
  },
];

@Component({
  selector: 'app-flows',
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
  templateUrl: './flows.component.html',
  styleUrl: './flows.component.css'
})
export class FlowsComponent {
  displayedColumns = ['srcIp', 'srcPort', 'destIp', 'destPort', 'protocol', 'packetsIn', 'bytesIn', 'packetsOut', 'bytesOut', 'duration'];
  dataSource: MatTableDataSource<NetworkFLow>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private renderer: Renderer2) {
    this.dataSource = new MatTableDataSource(DATA);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
}
