import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {SporociloModel} from "../../../../../assets/models/SporociloModel";
import {trace} from "../../../../utils/trace";
import {OsebaRepoService} from "../../../../core/repos/oseba/oseba-repo.service";
import {
  PrikaziPoslanoSporociloComponent
} from "../../../windows/prikazi-poslano-sporocilo/prikazi-poslano-sporocilo.component";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {DateOddaljenostPipe} from "../../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-table-sporocila',
  templateUrl: './table-sporocila.component.html',
  styleUrls: ['./table-sporocila.component.scss'],
  imports: [
    MatInputModule,
    MatTableModule,
    MatListModule,
    MatPaginatorModule,
    DateOddaljenostPipe,
    DatePipe
  ],
  standalone: true
})
export class TableSporocilaComponent implements OnInit {

  @Input() displayedColumns = ["smer", "pred", "posiljatelj", "prejemnik", "datum"]
  @Input() sporocila: MatTableDataSource<SporociloModel> = new MatTableDataSource<SporociloModel>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private osebaRepo: OsebaRepoService,
    private dialog: MatDialog) {
  }

  @trace()
  async ngOnInit() {
    this.sporocila.data = await this.osebaRepo.sporocila()
  }

  @trace()
  ngAfterViewInit() {
    this.sporocila.paginator = this.paginator;
    this.sporocila.sort = this.sort;
    this.sporocila.filterPredicate = this.filterPredicate
  }

  @trace()
  odpriDialog(sporociloInfo: SporociloModel) {
    this.dialog.open(PrikaziPoslanoSporociloComponent, {data: sporociloInfo});
  }

  @trace()
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.sporocila.filter = filterValue.trim()
  }

  @trace()
  filterPredicate(data: SporociloModel, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
