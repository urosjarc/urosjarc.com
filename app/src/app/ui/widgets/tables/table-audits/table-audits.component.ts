import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {Audit} from "../../../../core/services/api/models/audit";
import {DbService} from "../../../../core/services/db/db.service";
import {trace} from "../../../../utils/trace";
import {ime} from "../../../../utils/types";
import {MatInputModule} from "@angular/material/input";
import {DurationPipe} from "../../../pipes/duration/duration.pipe";
import {DateOddaljenostPipe} from "../../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {DatePipe} from "@angular/common";
import {MatListModule} from "@angular/material/list";

@Component({
  selector: 'app-table-audits',
  templateUrl: './table-audits.component.html',
  styleUrls: ['./table-audits.component.scss'],
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
export class TableAuditsComponent implements OnInit, AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() audits = new MatTableDataSource<Audit>()
  displayedColumns = ["tip", "opis", "trajanje", "ustvarjeno", "ustvarjeno_pred"]

  constructor(private dbService: DbService) {
  }

  @trace()
  async ngOnInit() {
    const root_id = this.dbService.get_profil_id()
    this.audits.data = await this.dbService.audit.where(ime<Audit>("entitete_id")).equals(root_id as string).toArray()
  }

  ngAfterViewInit() {
    this.audits.paginator = this.paginator;
    this.audits.sort = this.sort;
    this.audits.filterPredicate = this.filterAuditsPredicate
  }

  applyAuditsFilter($event: KeyboardEvent) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.audits.filter = filterValue.trim()
  }

  filterAuditsPredicate(data: Audit, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
