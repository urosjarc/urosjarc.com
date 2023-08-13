import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../utils";
import {DataService} from "../../services/data/data.service";
import {Zvezek} from "../../services/api/openapi/models/zvezek";

@Component({
  selector: 'app-table-zvezki',
  templateUrl: './table-zvezki.component.html',
  styleUrls: ['./table-zvezki.component.scss']
})
export class TableZvezkiComponent implements OnInit, AfterViewInit {

  @Input() displayedColumns = ["smer", "pred", "posiljatelj", "prejemnik", "datum"]
  @Input() zvezki: MatTableDataSource<Zvezek> = new MatTableDataSource<Zvezek>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(private data: DataService) {
  }

  @trace()
  async ngOnInit() {
    this.zvezki.data = await this.data.zvezki()
  }

  @trace()
  ngAfterViewInit() {
    this.zvezki.paginator = this.paginator;
    this.zvezki.sort = this.sort;
    this.zvezki.filterPredicate = this.filterPredicate
  }

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.zvezki.filter = filterValue.trim()
  }

  @trace()
  filterPredicate(data: Zvezek, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
