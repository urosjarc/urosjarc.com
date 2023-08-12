import { Component } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {SporociloInfo} from "../../../components/dialog-sporocilo/SporociloInfo";
import {DataService} from "../../../services/data/data.service";

@Component({
  selector: 'app-ucitelj-sporocila',
  templateUrl: './ucitelj-sporocila.component.html',
  styleUrls: ['./ucitelj-sporocila.component.scss']
})
export class UciteljSporocilaComponent {
  sporocila: MatTableDataSource<SporociloInfo> = new MatTableDataSource<SporociloInfo>()

  constructor(private data: DataService) {
  }

  async ngOnInit() {
    this.sporocila.data = await this.data.sporocila()
  }
}
