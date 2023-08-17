import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../utils";
import {DialogSporociloComponent} from "../dialog-sporocilo/dialog-sporocilo.component";
import {DataService} from "../../services/data/data.service";
import {MatDialog} from "@angular/material/dialog";
import {SporociloModel} from "../../models/SporociloModel";

@Component({
  selector: 'app-table-sporocila',
  templateUrl: './table-sporocila.component.html',
  styleUrls: ['./table-sporocila.component.scss']
})
export class TableSporocilaComponent implements OnInit {

  @Input() displayedColumns = ["smer", "pred", "posiljatelj", "prejemnik", "datum"]
  @Input() sporocila: MatTableDataSource<SporociloModel> = new MatTableDataSource<SporociloModel>()

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private data: DataService,
    private dialog: MatDialog) {
  }

  @trace()
  async ngOnInit() {
    this.sporocila.data = await this.data.sporocila()
  }

  @trace()
  ngAfterViewInit() {
    this.sporocila.paginator = this.paginator;
    this.sporocila.sort = this.sort;
    this.sporocila.filterPredicate = this.filterPredicate
  }

  @trace()
  odpriDialog(sporociloInfo: SporociloModel) {
    this.dialog.open(DialogSporociloComponent, {data: sporociloInfo});
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
