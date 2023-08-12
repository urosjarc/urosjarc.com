import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {DbService} from "../../../services/db/db.service";
import {TableOseba} from "../../../components/table-osebe/TableOseba";
import {DataService} from "../../../services/data/data.service";

@Component({
  selector: 'app-ucitelj-ucenci',
  templateUrl: './ucitelj-ucenci.component.html',
  styleUrls: ['./ucitelj-ucenci.component.scss']
})
export class UciteljUcenciComponent implements OnInit {
  ucenci: MatTableDataSource<TableOseba> = new MatTableDataSource()

  constructor(private data: DataService) {}

  async ngOnInit() {
    this.ucenci.data = await this.data.uciteljevi_ucenci()
  }
}
