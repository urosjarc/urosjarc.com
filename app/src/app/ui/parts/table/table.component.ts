import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {trace} from "../../../utils/trace";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {TableModel} from "./table.model";
import {RouterLink} from "@angular/router";
import {Object_plitka_enakost} from "../../../utils/Object";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  standalone: true,
  imports: [CommonModule, MatInputModule, MatTableModule, MatSortModule, MatListModule, MatPaginatorModule, RouterLink],
})
export class TableComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() columns: string[] = []
  @Input() dataSource: MatTableDataSource<any & TableModel> = new MatTableDataSource<any & TableModel>()
  @Input() selectionModel = new SelectionModel<any>(true, []);
  @Input() canSelect: boolean = false

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
  filterPredicate(data: TableModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  izberi(data: TableModel) {
    for (const selected of this.selectionModel.selected) {
      if (Object_plitka_enakost(data, selected)) return this.selectionModel.toggle(selected)
    }
    this.selectionModel.select(data)
  }

  isSelected(row: any) {
    for (const selected of this.selectionModel.selected) {
      if (Object_plitka_enakost(row, selected)) return true
    }
    return false
  }
}
