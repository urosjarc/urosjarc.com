import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../utils";
import {Naloga} from "../../services/api/openapi/models/naloga";

@Component({
  selector: 'app-table-naloge',
  templateUrl: './table-naloge.component.html',
  styleUrls: ['./table-naloge.component.scss']
})
export class TableNalogeComponent implements AfterViewInit {

  @Input() displayedColumns = ["smer", "pred", "posiljatelj", "prejemnik", "datum"]
  @Input() naloge: MatTableDataSource<Naloga> = new MatTableDataSource<Naloga>()

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
  filterPredicate(data: Naloga, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
