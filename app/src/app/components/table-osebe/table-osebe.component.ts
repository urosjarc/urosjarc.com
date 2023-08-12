import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {TableOseba} from "./TableOseba";
import {Audit} from "../../services/api/openapi/models/audit";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-table-osebe',
  templateUrl: './table-osebe.component.html',
  styleUrls: ['./table-osebe.component.scss']
})
export class TableOsebeComponent implements AfterViewInit {

  @Input() osebe: MatTableDataSource<TableOseba> = new MatTableDataSource()
  @Input() displayedColumns: any[] = ['naziv', 'datum', 'oddaljenost'];

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  applyOsebaFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.osebe.filter = filterValue.trim()
  }
  ngAfterViewInit() {
    this.osebe.paginator = this.paginator;
    this.osebe.sort = this.sort;
    this.osebe.filterPredicate = this.filterOsebePredicate
  }


  filterOsebePredicate(data: TableOseba, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
