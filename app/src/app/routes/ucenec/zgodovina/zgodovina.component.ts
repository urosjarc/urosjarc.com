import {Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {db} from "../../../db";
import {ime} from "../../../utils";
import {Audit} from "../../../api";

@Component({
  selector: 'app-zgodovina',
  templateUrl: './zgodovina.component.html',
  styleUrls: ['./zgodovina.component.scss']
})
export class ZgodovinaComponent {

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  audits = new MatTableDataSource<Audit>()
  displayedColumns = ["tip", "opis", "trajanje", "ustvarjeno", "ustvarjeno_pred"]

  constructor() {
  }

  ngAfterViewInit() {
    this.audits.paginator = this.paginator;
    this.audits.sort = this.sort;
    this.audits.filterPredicate = this.filterAuditsPredicate
  }

  ngOnInit() {
    this.initAudits()
  }

  async initAudits() {
    const root_id = await db.get_root_id()
    this.audits.data = await db.audit.where(ime<Audit>("entitete_id")).equals(root_id).toArray()
  }

  applyAuditsFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.audits.filter = filterValue.trim()
  }

  filterAuditsPredicate(data: Audit, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
