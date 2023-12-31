import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {DatePipe} from "@angular/common";
import {DateOddaljenostPipe} from "../../../ui/pipes/date-oddaljenost/date-oddaljenost.pipe";
import {trace} from "../../../utils/trace";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {UcenecDeloModel} from "./ucenec-delo.model";
import {DurationPipe} from "../../../ui/pipes/duration/duration.pipe";
import {String_vDate} from "../../../utils/String";
import {TableComponent} from "../../../ui/parts/table/table.component";

@Component({
  selector: 'app-ucenec-delo',
  templateUrl: './ucenec-delo.component.html',
  styleUrls: ['./ucenec-delo.component.scss'],
  imports: [
    TableComponent
  ],
  standalone: true
})
export class UcenecDeloComponent {
  audits: MatTableDataSource<UcenecDeloModel> = new MatTableDataSource<UcenecDeloModel>()
  audits_columns: (keyof UcenecDeloModel)[] = ["Tip", "Opis", "Trajanje", "Ustvarjeno", "Pred..."]

  constructor(
    private datePipe: DatePipe,
    private dateOddaljenostPipe: DateOddaljenostPipe,
    private durationPipe: DurationPipe,
    private osebaRepo: OsebaRepoService) {
  }

  @trace()
  async ngOnInit() {
    const audits = await this.osebaRepo.audits()

    const models: UcenecDeloModel[] = []

    for (const audit of audits) {
      models.push({
        Tip: audit.tip,
        Opis: audit.opis,
        Trajanje: this.durationPipe.transform(audit.trajanje),
        Ustvarjeno: this.datePipe.transform(audit.ustvarjeno) || "",
        "Pred...": this.dateOddaljenostPipe.transform(String_vDate(audit.ustvarjeno))
      })
    }

    this.audits.data = models
  }
}
