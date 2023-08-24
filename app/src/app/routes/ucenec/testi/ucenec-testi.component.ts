import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UcenecRepoService} from "../../../core/repos/ucenec/ucenec-repo.service";
import {TestModel} from "../../../../assets/models/TestModel";
import {PrikaziTesteComponent} from "../../../ui/widgets/tables/prikazi-teste/prikazi-teste.component";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-ucenec-testi',
  templateUrl: './ucenec-testi.component.html',
  styleUrls: ['./ucenec-testi.component.scss'],
  imports: [
    PrikaziTesteComponent
  ],
  standalone: true
})
export class UcenecTestiComponent implements OnInit {
  testi: MatTableDataSource<TestModel> = new MatTableDataSource()

  constructor(private ucenecRepo: UcenecRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.testi.data = await this.ucenecRepo.testi()
  }
}
