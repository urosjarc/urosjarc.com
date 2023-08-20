import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {TematikaModel} from "../../../../../assets/models/TematikaModel";
import {trace} from "../../../../utils/trace";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-table-tematike',
  templateUrl: './table-tematike.component.html',
  styleUrls: ['./table-tematike.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    MatListModule,
    NgClass,
    MatPaginatorModule
  ],
  standalone: true
})
export class TableTematikeComponent implements AfterViewInit {

  @Input() displayedColumns = ["naslov"]
  @Input() tematike: MatTableDataSource<TematikaModel> = new MatTableDataSource<TematikaModel>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @trace()
  ngAfterViewInit() {
    this.tematike.paginator = this.paginator;
    this.tematike.sort = this.sort;
    this.tematike.filterPredicate = this.filterPredicate
  }

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.tematike.filter = filterValue.trim()
  }

  @trace()
  filterPredicate(data: TematikaModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  @trace()
  izberi(tematika: TematikaModel) {
    tematika.izbran = tematika.izbran ? false : true
  }
}
