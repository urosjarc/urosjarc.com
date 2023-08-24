import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {NgClass} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {NalogaModel} from "../../../core/domain/NalogaModel";
import {trace} from "../../../utils/trace";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-table-naloge',
  templateUrl: './izberi-naloge.component.html',
  styleUrls: ['./izberi-naloge.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    NgClass,
    MatListModule,
    MatPaginatorModule
  ],
  standalone: true
})
export class IzberiNalogeComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @Input() displayedColumns = ["naslov"]
  @Input() dataSource: MatTableDataSource<NalogaModel> = new MatTableDataSource<NalogaModel>()
  selectionModel = new SelectionModel<NalogaModel>(true, []);

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
  filterPredicate(data: NalogaModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  izberi(naloga: NalogaModel) {
    this.selectionModel.toggle(naloga)
  }
}
