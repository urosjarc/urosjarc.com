import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UcenecRepoService} from "../../../core/repos/ucenec/ucenec-repo.service";
import {trace} from "../../../utils/trace";
import {TestModel} from "../../../core/domain/TestModel";
import {TableComponent} from "../../../ui/parts/table/table.component";
import {ime} from "../../../utils/types";

@Component({
  selector: 'app-ucenec-testi',
  templateUrl: './ucenec-testi.component.html',
  styleUrls: ['./ucenec-testi.component.scss'],
  imports: [
    TableComponent
  ],
  standalone: true
})
export class UcenecTestiComponent implements OnInit {
  testi: MatTableDataSource<TestModel> = new MatTableDataSource()
  testi_columns: string[] = [
    ime<TestModel>('deadline'),
    ime<TestModel>('opravljeno'),
  ]

  constructor(private ucenecRepo: UcenecRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.testi.data = await this.ucenecRepo.testi()
  }
}
