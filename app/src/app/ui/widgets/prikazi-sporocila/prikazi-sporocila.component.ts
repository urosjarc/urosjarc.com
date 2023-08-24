import {Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {DatePipe} from "@angular/common";
import {Dialog} from "@angular/cdk/dialog";
import {DateOddaljenostPipe} from "../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {SporociloModel} from "../../../core/domain/SporociloModel";
import {trace} from "../../../utils/trace";
import {
  PrikaziPoslanoSporociloComponent
} from "../../windows/prikazi-poslano-sporocilo/prikazi-poslano-sporocilo.component";

@Component({
  selector: 'app-table-sporocila',
  templateUrl: './prikazi-sporocila.component.html',
  styleUrls: ['./prikazi-sporocila.component.scss'],
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
export class PrikaziSporocilaComponent {

  @Input() displayedColumns = ["smer", "pred", "posiljatelj", "prejemnik", "datum"]
  @Input() sporocila: MatTableDataSource<SporociloModel> = new MatTableDataSource<SporociloModel>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(private dialog: Dialog) {
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
