import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {NgClass} from "@angular/common";
import {SelectionModel} from "@angular/cdk/collections";
import {TematikaModel} from "../../../core/domain/TematikaModel";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-table-tematike',
  templateUrl: './izberi-tematike.component.html',
  styleUrls: ['./izberi-tematike.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    MatListModule,
    NgClass,
    MatPaginatorModule
  ],
  standalone: true
})
export class IzberiTematikeComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() displayedColumns = ["naslov"]
  @Input() dataSource: MatTableDataSource<TematikaModel> = new MatTableDataSource<TematikaModel>()
  selectionModel = new SelectionModel<TematikaModel>(true, []);

  @trace()
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource.filterPredicate = this.filterPredicate
  }

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim()
  }

  @trace()
  filterPredicate(data: TematikaModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  @trace()
  izberi(tematika: TematikaModel) {
    this.selectionModel.toggle(tematika)
  }
}
