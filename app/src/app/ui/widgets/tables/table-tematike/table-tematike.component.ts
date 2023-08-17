import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../utils";
import {TematikaModel} from "../../models/TematikaModel";

@Component({
  selector: 'app-table-tematike',
  templateUrl: './table-tematike.component.html',
  styleUrls: ['./table-tematike.component.scss']
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
