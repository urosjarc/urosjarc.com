import {Component, OnInit} from '@angular/core';
import {DataService} from "../../../services/data/data.service";
import {MatTableDataSource} from "@angular/material/table";
import {TestModel} from "../../../models/TestModel";

@Component({
  selector: 'app-ucenec-testi',
  templateUrl: './ucenec-testi.component.html',
  styleUrls: ['./ucenec-testi.component.scss']
})
export class UcenecTestiComponent implements OnInit {
  testi: MatTableDataSource<TestModel> = new MatTableDataSource()

  constructor(private data: DataService) {
  }

  async ngOnInit() {
    this.testi.data = await this.data.ucencevi_testi()
  }
}
