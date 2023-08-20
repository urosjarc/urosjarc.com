import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {OsebaRepoService} from "../../../../core/repos/oseba/oseba-repo.service";
import {ZvezekModel} from "../../../../../assets/models/ZvezekModel";
import {trace} from "../../../../utils/trace";
import {Zvezek} from "../../../../core/services/api/models/zvezek";
import {MatInputModule} from "@angular/material/input";
import {NgClass} from "@angular/common";
import {MatListModule} from "@angular/material/list";

@Component({
  selector: 'app-table-zvezki',
  templateUrl: './table-zvezki.component.html',
  styleUrls: ['./table-zvezki.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    NgClass,
    MatListModule,
    MatPaginatorModule
  ],
  standalone: true
})
export class TableZvezkiComponent implements OnInit, AfterViewInit {

  @Input() displayedColumns = ["tip", "naslov"]
  @Input() zvezki: MatTableDataSource<ZvezekModel> = new MatTableDataSource<ZvezekModel>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(private osebaRepo: OsebaRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.zvezki.data = await this.osebaRepo.zvezki()
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
  filterPredicate(data: ZvezekModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }

  izberi(zvezek: ZvezekModel) {
    zvezek.izbran = zvezek.izbran ? false : true
  }
}
