import {Component, Input, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Audit} from "../../api/models";

@Component({
  selector: 'app-audits-tabela',
  templateUrl: './audits-tabela.component.html',
  styleUrls: ['./audits-tabela.component.scss']
})
export class AuditsTabelaComponent {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() audits = new MatTableDataSource<Audit>()
  displayedColumns = ["tip", "opis", "trajanje", "ustvarjeno", "ustvarjeno_pred"]

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
