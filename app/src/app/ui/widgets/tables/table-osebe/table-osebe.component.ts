import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {OsebaModel} from "../../../../../assets/models/OsebaModel";
import {trace} from "../../../../utils/trace";

@Component({
  selector: 'app-table-osebe',
  templateUrl: './table-osebe.component.html',
  styleUrls: ['./table-osebe.component.scss'],
  standalone: true
})
export class TableOsebeComponent implements AfterViewInit {

  @Input() osebe: MatTableDataSource<OsebaModel> = new MatTableDataSource<OsebaModel>()
  @Input() displayedColumns: any[] = ['naziv', 'datum', 'oddaljenost'];

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.osebe.filter = filterValue.trim()
  }

  ngAfterViewInit() {
    this.osebe.paginator = this.paginator;
    this.osebe.sort = this.sort;
    this.osebe.filterPredicate = this.filterPredicate
  }


  filterPredicate(data: OsebaModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
