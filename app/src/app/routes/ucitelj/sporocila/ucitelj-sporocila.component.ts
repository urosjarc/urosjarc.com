import { Component } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {DataService} from "../../../services/data/data.service";
import {SporociloInfo} from "../../../services/data/SporociloInfo";
import {trace} from "../../../utils";

@Component({
  selector: 'app-ucitelj-sporocila',
  templateUrl: './ucitelj-sporocila.component.html',
  styleUrls: ['./ucitelj-sporocila.component.scss']
})
export class UciteljSporocilaComponent {
  sporocila: MatTableDataSource<SporociloInfo> = new MatTableDataSource<SporociloInfo>()

  constructor(private data: DataService) {
  }

  @trace()
  async ngOnInit() {
    this.sporocila.data = await this.data.sporocila()
  }
}
