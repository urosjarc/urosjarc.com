import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Audit} from "../../services/api/openapi/models/audit";
import {DbService} from "../../services/db/db.service";
import {ime, trace} from "../../utils";

@Component({
  selector: 'app-audits-tabela',
  templateUrl: './audits-tabela.component.html',
  styleUrls: ['./audits-tabela.component.scss']
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
    const root_id = this.dbService.get_root_id()
    this.audits.data = await this.dbService.audit.where(ime<Audit>("entitete_id")).equals(root_id).toArray()
  }

  ngAfterViewInit() {
    this.audits.paginator = this.paginator;
    this.audits.sort = this.sort;
    this.audits.filterPredicate = this.filterAuditsPredicate
  }

  applyAuditsFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.audits.filter = filterValue.trim()
  }

  filterAuditsPredicate(data: Audit, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
