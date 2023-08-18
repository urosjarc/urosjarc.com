import {Component, Input, OnInit} from '@angular/core';
import {DataService} from "../../../services/data/data.service";
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../../utils";
import {TestModel} from "../../../models/TestModel";

@Component({
  selector: 'app-ucitelj-testi',
  templateUrl: './ucitelj-testi.component.html',
  styleUrls: ['./ucitelj-testi.component.scss'],
  standalone: true
})
export class UciteljTestiComponent implements OnInit {
  testi: MatTableDataSource<TestModel> = new MatTableDataSource()
  displayedColumns: any[] = ['naslov', 'datum', 'oddaljenost'];

  constructor(private data: DataService) {
  }

  @trace()
  async ngOnInit() {
    this.testi.data = await this.data.uciteljevi_testi()
  }
}
