import { Component } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {DataService} from "../../../services/data/data.service";
import {trace} from "../../../utils";
import {SporociloModel} from "../../../models/SporociloModel";

@Component({
  selector: 'app-ucitelj-sporocila',
  templateUrl: './ucitelj-sporocila.component.html',
  styleUrls: ['./ucitelj-sporocila.component.scss'],
  standalone: true
})
export class UciteljSporocilaComponent {
  sporocila: MatTableDataSource<SporociloModel> = new MatTableDataSource<SporociloModel>()

  constructor(private data: DataService) {
  }

  @trace()
  async ngOnInit() {
    this.sporocila.data = await this.data.sporocila()
  }
}
