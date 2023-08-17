import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../utils";
import {NalogaModel} from "../../models/NalogaModel";

@Component({
  selector: 'app-table-naloge',
  templateUrl: './table-naloge.component.html',
  styleUrls: ['./table-naloge.component.scss'],
  standalone: true
})
export class TableNalogeComponent implements AfterViewInit {

  @Input() displayedColumns = ["naslov"]
  @Input() naloge: MatTableDataSource<NalogaModel> = new MatTableDataSource<NalogaModel>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @trace()
  ngAfterViewInit() {
    this.naloge.paginator = this.paginator;
    this.naloge.sort = this.sort;
    this.naloge.filterPredicate = this.filterPredicate
  }

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.naloge.filter = filterValue.trim()
  }

  @trace()
  filterPredicate(data: NalogaModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  izberi(naloga: NalogaModel) {
    naloga.izbran = naloga.izbran ? false : true
  }
}
