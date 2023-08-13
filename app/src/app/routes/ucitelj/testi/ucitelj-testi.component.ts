import {Component, Input, OnInit} from '@angular/core';
import {DataService} from "../../../services/data/data.service";
import {MatTableDataSource} from "@angular/material/table";
import {TestInfo} from "../../../services/data/TestInfo";
import {trace} from "../../../utils";

@Component({
  selector: 'app-ucitelj-testi',
  templateUrl: './ucitelj-testi.component.html',
  styleUrls: ['./ucitelj-testi.component.scss']
})
export class UciteljTestiComponent implements OnInit {
  testi: MatTableDataSource<TestInfo> = new MatTableDataSource()
  displayedColumns: any[] = ['naslov', 'datum', 'oddaljenost'];

  constructor(private data: DataService) {
  }

  @trace()
  async ngOnInit() {
    this.testi.data = await this.data.uciteljevi_testi()
  }
}
