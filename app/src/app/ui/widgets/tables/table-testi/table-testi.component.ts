import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {TestModel} from "../../../../../assets/models/TestModel";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {DateOddaljenostPipe} from "../../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {DateOddaljenostClassPipe} from "../../../pipes/date-oddaljenost-class/date-oddaljenost-class.pipe";
import {DatePipe, PercentPipe} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-table-testi',
  templateUrl: './table-testi.component.html',
  styleUrls: ['./table-testi.component.scss'],
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
export class TableTestiComponent implements AfterViewInit {
  @Input() testi: MatTableDataSource<TestModel> = new MatTableDataSource<TestModel>()
  @Input() displayedColumns: any[] = ['naslov', 'opravljeno', 'datum', 'oddaljenost'];
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

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
