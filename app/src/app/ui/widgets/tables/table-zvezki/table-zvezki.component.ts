import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../utils";
import {DataService} from "../../services/data/data.service";
import {Zvezek} from "../../services/api/openapi/models/zvezek";
import {ZvezekModel} from "../../models/ZvezekModel";

@Component({
  selector: 'app-table-zvezki',
  templateUrl: './table-zvezki.component.html',
  styleUrls: ['./table-zvezki.component.scss'],
  standalone: true
})
export class TableZvezkiComponent implements OnInit, AfterViewInit {

  @Input() displayedColumns = ["tip", "naslov"]
  @Input() zvezki: MatTableDataSource<ZvezekModel> = new MatTableDataSource<ZvezekModel>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(private data: DataService) {
  }

  @trace()
  async ngOnInit() {
    this.zvezki.data = await this.data.zvezki()
    console.log(this.zvezki.data)
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

  izberi(zvezek: ZvezekModel) {
    zvezek.izbran = zvezek.izbran ? false : true
  }
}
