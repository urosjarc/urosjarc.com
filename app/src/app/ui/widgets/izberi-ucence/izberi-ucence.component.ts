import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatInputModule} from "@angular/material/input";
import {NgClass} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {OsebaModel} from "../../../core/domain/OsebaModel";
import {trace} from "../../../utils/trace";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-table-ucenci',
  templateUrl: './izberi-ucence.component.html',
  styleUrls: ['./izberi-ucence.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    NgClass,
    MatListModule,
    MatPaginatorModule
  ],
  standalone: true
})
export class IzberiUcenceComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() displayedColumns: any[] = ['naziv'];
  @Input() dataSource: MatTableDataSource<OsebaModel> = new MatTableDataSource<OsebaModel>()
  selectionModel = new SelectionModel<OsebaModel>(true, []);

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim()
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource.filterPredicate = this.filterPredicate
  }

  filterPredicate(data: OsebaModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  izberi(ucenec: OsebaModel) {
    this.selectionModel.toggle(ucenec)
  }
}
