import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {trace} from "../../../utils/trace";
import {TestModel} from "../../../core/domain/TestModel";
import {TableComponent} from "../../../ui/parts/table/table.component";
import {UcenecTestiModel} from "../../ucenec/testi/ucenec-testi.model";
import {DateOddaljenostClassPipe} from "../../../ui/pipes/date-oddaljenost-class/date-oddaljenost-class.pipe";
import {DatePipe, PercentPipe} from "@angular/common";
import {DateOddaljenostPipe} from "../../../ui/pipes/date-oddaljenost/date-oddaljenost.pipe";
import {UcenecRepoService} from "../../../core/repos/ucenec/ucenec-repo.service";
import {appUrls} from "../../../app.urls";
import {UciteljTestiModel} from "./ucitelj-testi.model";

@Component({
  selector: 'app-ucitelj-testi',
  templateUrl: './ucitelj-testi.component.html',
  styleUrls: ['./ucitelj-testi.component.scss'],
  imports: [
    TableComponent
  ],
  standalone: true
})
export class UciteljTestiComponent implements OnInit {
  testi: MatTableDataSource<UcenecTestiModel> = new MatTableDataSource<UcenecTestiModel>()
  testi_columns: (keyof UcenecTestiModel)[] = ['Naslov', "Nalog", 'Deadline', 'Čez...']

  constructor(
    private dateOddaljenostClassPipe: DateOddaljenostClassPipe,
    private datePipe: DatePipe,
    private percentPipe: PercentPipe,
    private dateOddaljenostPipe: DateOddaljenostPipe,
    private uciteljRepo: UciteljRepoService) {
  }

  @trace()
  async ngOnInit() {
    const testi = await this.uciteljRepo.testi()
    const models: UcenecTestiModel[] = []

    for (const test of testi) {
      models.push({
        Nalog:  test.test.naloga_id.length,
        Naslov: test.test.naslov,
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
