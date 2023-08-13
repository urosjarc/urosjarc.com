import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {DataService} from "../../services/data/data.service";
import {TestInfo} from "../../services/data/TestInfo";

@Component({
  selector: 'app-table-testi',
  templateUrl: './table-testi.component.html',
  styleUrls: ['./table-testi.component.scss']
})
export class TableTestiComponent implements AfterViewInit {
  @Input() testi: MatTableDataSource<TestInfo> = new MatTableDataSource()
  @Input() displayedColumns: any[] = ['naslov', 'opravljeno', 'datum', 'oddaljenost'];
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit() {
    this.testi.paginator = this.paginator;
    this.testi.sort = this.sort;
    this.testi.filterPredicate = this.filterTestPredicate
  }

  filterTestPredicate(data: TestInfo, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  applyTestFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.testi.filter = filterValue.trim()
  }


}
