import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {trace} from "../../utils";
import {OsebaModel} from "../../models/OsebaModel";

@Component({
  selector: 'app-table-ucenci',
  templateUrl: './table-ucenci.component.html',
  styleUrls: ['./table-ucenci.component.scss'],
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
