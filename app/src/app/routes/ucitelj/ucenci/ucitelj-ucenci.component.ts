import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {DataService} from "../../../services/data/data.service";
import {trace} from "../../../utils";
import {OsebaModel} from "../../../models/OsebaModel";

@Component({
  selector: 'app-ucitelj-ucenci',
  templateUrl: './ucitelj-ucenci.component.html',
  styleUrls: ['./ucitelj-ucenci.component.scss']
})
export class UciteljUcenciComponent implements OnInit {
  ucenci: MatTableDataSource<OsebaModel> = new MatTableDataSource()

  constructor(private data: DataService) {
  }

  @trace()
  async ngOnInit() {
    this.ucenci.data = await this.data.uciteljevi_ucenci()
  }
}
