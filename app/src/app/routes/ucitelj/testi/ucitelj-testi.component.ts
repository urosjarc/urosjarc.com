import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {trace} from "../../../utils/trace";
import {TestModel} from "../../../core/domain/TestModel";

@Component({
  selector: 'app-ucitelj-testi',
  templateUrl: './ucitelj-testi.component.html',
  styleUrls: ['./ucitelj-testi.component.scss'],
  imports: [
  ],
  standalone: true
})
export class UciteljTestiComponent implements OnInit {
  testi: MatTableDataSource<TestModel> = new MatTableDataSource()

  constructor(private uciteljRepo: UciteljRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.testi.data = await this.uciteljRepo.testi()
  }
}
