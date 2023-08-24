import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {DatePipe} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {DurationPipe} from "../../pipes/duration/duration.pipe";
import {DateOddaljenostPipe} from "../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {Audit} from "../../../core/services/api/models/audit";

@Component({
  selector: 'app-table-audits',
  templateUrl: './prikazi-audits.component.html',
  styleUrls: ['./prikazi-audits.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    DurationPipe,
    DateOddaljenostPipe,
    DatePipe,
    MatPaginatorModule,
    MatListModule,
    MatSortModule
  ],
  standalone: true
})
export class PrikaziAuditsComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns = ["tip", "opis", "trajanje", "ustvarjeno", "ustvarjeno_pred"]
  @Input() dataSource = new MatTableDataSource<Audit>()

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource.filterPredicate = this.filterAuditsPredicate
  }

  applyAuditsFilter($event: KeyboardEvent) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim()
  }

  filterAuditsPredicate(data: Audit, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
