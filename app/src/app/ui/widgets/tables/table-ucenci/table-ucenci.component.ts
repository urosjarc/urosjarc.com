import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {OsebaModel} from "../../../../../assets/models/OsebaModel";
import {trace} from "../../../../utils/trace";
import {MatInputModule} from "@angular/material/input";
import {NgClass} from "@angular/common";
import {MatListModule} from "@angular/material/list";

@Component({
  selector: 'app-table-ucenci',
  templateUrl: './table-ucenci.component.html',
  styleUrls: ['./table-ucenci.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    NgClass,
    MatListModule,
    MatPaginatorModule
  ],
  standalone: true
})
export class TableUcenciComponent implements AfterViewInit {

  @Input() ucenci: MatTableDataSource<OsebaModel> = new MatTableDataSource<OsebaModel>()
  @Input() displayedColumns: any[] = ['naziv'];

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.ucenci.filter = filterValue.trim()
  }

  ngAfterViewInit() {
    this.ucenci.paginator = this.paginator;
    this.ucenci.sort = this.sort;
    this.ucenci.filterPredicate = this.filterPredicate
  }


  filterPredicate(data: OsebaModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  izberi(ucenec: OsebaModel) {
    ucenec.izbran = ucenec.izbran ? false : true
  }
}
