import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Test} from "../../services/api/openapi/models/test";
import {TestModel} from "../../models/TestModel";

@Component({
  selector: 'app-table-testi',
  templateUrl: './table-testi.component.html',
  styleUrls: ['./table-testi.component.scss']
})
export class TableTestiComponent implements AfterViewInit {
  @Input() testi: MatTableDataSource<TestModel> = new MatTableDataSource<TestModel>()
  @Input() displayedColumns: any[] = ['naslov', 'opravljeno', 'datum', 'oddaljenost'];
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit() {
    this.testi.paginator = this.paginator;
    this.testi.sort = this.sort;
    this.testi.filterPredicate = this.filterPredicate
  }

  filterPredicate(data: Test, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.testi.filter = filterValue.trim()
  }


}
