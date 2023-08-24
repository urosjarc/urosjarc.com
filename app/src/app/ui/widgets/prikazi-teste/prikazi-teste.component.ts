import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {DatePipe, PercentPipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {DateOddaljenostPipe} from "../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {DateOddaljenostClassPipe} from "../../pipes/date-oddaljenost-class/date-oddaljenost-class.pipe";
import {TestModel} from "../../../core/domain/TestModel";

@Component({
  selector: 'app-table-testi',
  templateUrl: './prikazi-teste.component.html',
  styleUrls: ['./prikazi-teste.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatListModule,
    MatSortModule,
    DateOddaljenostPipe,
    DateOddaljenostClassPipe,
    PercentPipe,
    DatePipe,
    RouterLink
  ],
  standalone: true
})
export class PrikaziTesteComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() testi: MatTableDataSource<TestModel> = new MatTableDataSource<TestModel>()
  @Input() displayedColumns: any[] = ['naslov', 'opravljeno', 'datum', 'oddaljenost'];

  ngAfterViewInit() {
    this.testi.paginator = this.paginator;
    this.testi.sort = this.sort;
    this.testi.filterPredicate = this.filterPredicate
  }

  filterPredicate(data: TestModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.testi.filter = filterValue.trim()
  }
}
