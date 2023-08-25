import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {DatePipe} from "@angular/common";
import {DateOddaljenostPipe} from "../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {trace} from "../../../utils/trace";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {PrikaziAuditsOsebeModel} from "./prikazi-audits-osebe.model";
import {DurationPipe} from "../../pipes/duration/duration.pipe";
import {String_vDate} from "../../../utils/String";
import {TableComponent} from "../../parts/table/table.component";

@Component({
  selector: 'app-prikazi-audits-osebe',
  templateUrl: './prikazi-audits-osebe.component.html',
  styleUrls: ['./prikazi-audits-osebe.component.scss'],
  imports: [
    TableComponent
  ],
  standalone: true
})
export class PrikaziAuditsOsebeComponent {
  audits: MatTableDataSource<PrikaziAuditsOsebeModel> = new MatTableDataSource<PrikaziAuditsOsebeModel>()
  audits_columns: string[] = ["Tip", "Opis", "Trajanje", "Ustvarjeno", "Pred..."]

  constructor(
    private datePipe: DatePipe,
    private dateOddaljenostPipe: DateOddaljenostPipe,
    private durationPipe: DurationPipe,
    private osebaRepo: OsebaRepoService) {
  }

  @trace()
  async ngOnInit() {
    const audits = await this.osebaRepo.audits()

    const models: PrikaziAuditsOsebeModel[] = []

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
