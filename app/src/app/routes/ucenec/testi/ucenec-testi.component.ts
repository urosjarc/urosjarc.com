import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UcenecRepoService} from "../../../core/repos/ucenec/ucenec-repo.service";
import {trace} from "../../../utils/trace";
import {TableComponent} from "../../../ui/parts/table/table.component";
import {UcenecTestiModel} from "./ucenec-testi.model";
import {DateOddaljenostPipe} from "../../../ui/pipes/date-oddaljenost/date-oddaljenost.pipe";
import {DatePipe, PercentPipe} from "@angular/common";
import {DateOddaljenostClassPipe} from "../../../ui/pipes/date-oddaljenost-class/date-oddaljenost-class.pipe";
import {appUrls} from "../../../app.urls";

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
  testi: MatTableDataSource<UcenecTestiModel> = new MatTableDataSource<UcenecTestiModel>()
  testi_columns: (keyof UcenecTestiModel)[] = ['Naslov', 'Nalog', 'Opravljeno', 'Deadline', 'Čez...']

  constructor(
    private dateOddaljenostClassPipe: DateOddaljenostClassPipe,
    private datePipe: DatePipe,
    private percentPipe: PercentPipe,
    private dateOddaljenostPipe: DateOddaljenostPipe,
    private ucenecRepo: UcenecRepoService) {
  }

  @trace()
  async ngOnInit() {
    const testi = await this.ucenecRepo.testi()
    const models: UcenecTestiModel[] = []

    for (const test of testi) {
      models.push({
        Naslov: test.test.naslov,
        Nalog: test.test.naloga_id.length,
        Deadline: this.datePipe.transform(test.deadline) || "",
        Opravljeno: this.percentPipe.transform(test.opravljeno) || "",
        ['Čez...']: this.dateOddaljenostPipe.transform(test.deadline),
        cls: this.dateOddaljenostClassPipe.transform(test.deadline),
        routerLink: appUrls.ucenec({}).test({test_id: test.test._id.toString()}).$
      })
    }

    this.testi.data = models
  }
}
