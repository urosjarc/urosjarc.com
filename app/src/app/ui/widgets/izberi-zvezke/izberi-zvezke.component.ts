import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {NgClass} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {SelectionModel} from "@angular/cdk/collections";
import {ZvezekModel} from "../../../core/domain/ZvezekModel";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-table-zvezki',
  templateUrl: './izberi-zvezke.component.html',
  styleUrls: ['./izberi-zvezke.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    NgClass,
    MatListModule,
    MatPaginatorModule
  ],
  standalone: true
})
export class IzberiZvezkeComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() displayedColumns = ["tip", "naslov"]
  @Input() dataSource: MatTableDataSource<ZvezekModel> = new MatTableDataSource<ZvezekModel>()
  selectionModel = new SelectionModel<ZvezekModel>(true, []);

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
  filterPredicate(data: ZvezekModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  izberi(zvezek: ZvezekModel) {
    this.selectionModel.toggle(zvezek)
  }
}
