import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import * as moment from "moment";
import {median, standardDeviation} from "simple-statistics";
import {MatDialog} from "@angular/material/dialog";
import {exe, ime} from "../../../../utils/types";
import {NalogaModel} from "../../../../../assets/models/NalogaModel";
import {ApiService} from "../../../../core/services/api/services/api.service";
import {DbService} from "../../../../core/services/db/db.service";
import {trace} from "../../../../utils/trace";
import {Test} from "../../../../core/services/api/models/test";
import {Audit} from "../../../../core/services/api/models/audit";
import {Status} from "../../../../core/services/api/models/status";
import {Naloga} from "../../../../core/services/api/models/naloga";
import {Tematika} from "../../../../core/services/api/models/tematika";
import {Oseba} from "../../../../core/services/api/models/oseba";
import {Id} from "../../../../core/services/api/models/id";
import {IzberiDatumComponent} from "../../../../ui/windows/izberi-datum/izberi-datum.component";
import {PieChartModule} from "@swimlane/ngx-charts";
import {MatListModule} from "@angular/material/list";
import {StatusTipStylePipe} from "../../../../ui/pipes/statusTip-style/statusTip-style.pipe";
import {DatePipe, KeyValuePipe, NgForOf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {DateOddaljenostPipe} from "../../../../ui/pipes/date-oddaljenost/date-oddaljenost.pipe";
import {routes} from "../../../../routes";

@Component({
  selector: 'app-ucenec-testi-test',
  templateUrl: './ucenec-testi-test.component.html',
  styleUrls: ['./ucenec-testi-test.component.scss'],
  imports: [
    PieChartModule,
    MatListModule,
    StatusTipStylePipe,
    KeyValuePipe,
    MatButtonModule,
    NgForOf,
    DatePipe,
    DateOddaljenostPipe,
    RouterLink
  ],
  standalone: true
})
export class UcenecTestiTestComponent implements OnInit {
  protected readonly routes = routes;

  test_id: string

  statistika_barve: any = {
    domain: ['red', 'orange', '#e1e1e1', 'forestgreen']
  };
  opravljeno_barve: any = {
    domain: ['forestgreen', 'red']
  };

  tema_nalogeInfo = new Map<string, NalogaModel[]>()
  statistika: { value: number, name: string }[] = [];
  opravljeno: { value: number, name: string }[] = [];
  delo: string = "";
  hitrost: string = "";
  test: string = "";
  deadline: Date = new Date();

  constructor(
    private dbService: DbService,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private apiService: ApiService
  ) {
    this.test_id = route.snapshot.paramMap.get("test_id") || ""
  }

  @trace()
  async ngOnInit() {
    const profil_id = this.dbService.get_profil_id()
    const test = await this.dbService.test.where({
      [ime<Test>("_id")]: this.test_id,
      [ime<Test>("oseba_ucenec_id")]: profil_id
    }).first()

    if (!test) return

    this.deadline = moment(test.deadline).toDate()
    await this.initStatistikaOpravljenoDelo(profil_id, test)
    await this.initCasovnaStatistika()
    await this.initNaloge(profil_id, test)
  }


  @trace()
  async initCasovnaStatistika() {

    const audit_tip: Audit['tip'] = 'STATUS_TIP_POSODOBITEV'
    const status_tip: Status['tip'] = 'PRAVILNO'
    const audits = await this.dbService.audit.where({
      [ime<Audit>("entitete_id")]: this.test_id,
      [ime<Audit>("tip")]: audit_tip,
      [ime<Audit>("opis")]: status_tip
    }).toArray()

    let trajanja_sek = audits.map(audit => moment.duration(audit.trajanje).asSeconds())
    let mediana = Math.round(median(trajanja_sek) / 60 * 10) / 10
    let deviacija = Math.round(standardDeviation(trajanja_sek) / 60 * 10) / 10
    let nalog_testa = Math.round(45 / mediana * 10) / 10

    this.test = `${nalog_testa} nalog v 45 minutah`
    this.hitrost = `(${mediana} ± ${deviacija}) minut na nalogo`
  }

  @trace()
  async initStatistikaOpravljenoDelo(root_id: Id<Oseba>, test: Test) {
    const statusi = await this.dbService.status.where({
      [ime<Status>("oseba_id")]: root_id,
      [ime<Status>("test_id")]: this.test_id,
    }).toArray()

    const statistika_num = new Map<string, number>()
    for (const status of statusi) {
      const status_tip: Status['tip'] = status.tip || 'NEZACETO'
      const st = statistika_num.get(status_tip as string) || 0
      statistika_num.set(status_tip as string, st + 1)
    }
    const status_tip: Status['tip'] = 'PRAVILNO'
    const pravilno = statistika_num.get(status_tip) || 0
    const vse_naloge = test.naloga_id?.length || 0
    const manjka = vse_naloge - pravilno

    const dni_ostalo = moment(test.deadline).diff(moment(), 'days')
    this.delo = `${dni_ostalo > 0 ? Math.ceil(manjka / dni_ostalo) : manjka} nalog na dan`

    const PRAVILNO: Status['tip'] = "PRAVILNO"
    this.opravljeno = [
      {
        name: PRAVILNO,
        value: pravilno
      },
      {
        name: "MANJKA",
        value: manjka
      }
    ]

    const statistika = []
    for (const [name, value] of statistika_num) {
      statistika.push({name, value})
    }
    this.statistika = statistika.sort((a, b) => a.name.localeCompare(b.name))
  }

  @trace()
  async initNaloge(root_id: Id<Oseba>, test: Test) {
    const naloga_ids = test.naloga_id?.map(ele => ele.toString()) || []

    let i = 0
    const tema_nalogeInfo = new Map<string, NalogaModel[]>()
    for (const nalogaId of naloga_ids) {
      const naloga = await this.dbService.naloga.where(ime<Naloga>("_id")).equals(nalogaId).first()

      if (!naloga) return

      const status = await this.dbService.status.where({
        [ime<Status>("naloga_id")]: naloga._id,
        [ime<Status>("oseba_id")]: root_id,
        [ime<Status>("test_id")]: this.test_id,
      }).first()

      const tema = await this.dbService.tematika.where(ime<Tematika>("_id")).equals((naloga.tematika_id || "") as string).first()

      if (!tema) continue
      if (!tema.naslov) continue

      const nalogaInfo: NalogaModel = {
        id: naloga._id || "",
        stevilka: i++,
        resitev: naloga.resitev || '',
        vsebina: naloga.vsebina || '',
        izbran: false,
        status: status
      }

      if (tema_nalogeInfo.has(tema.naslov)) {
        tema_nalogeInfo.get(tema.naslov)?.push(nalogaInfo)
      } else {
        tema_nalogeInfo.set(tema.naslov, [nalogaInfo])
      }
    }
    this.tema_nalogeInfo = tema_nalogeInfo
  }

  @trace()
  async nastaviDatum() {
    const self = this;
    let matDialogRef = this.dialog.open(IzberiDatumComponent, {
      data: this.deadline,
    });

    const datum: Date = await exe(matDialogRef.afterClosed())
    const deadline = moment(datum).toISOString(true).split("T")[0]
    const res = await exe(this.apiService.ucenecTestTestIdPut({test_id: this.test_id, body: {datum: deadline}}))

    if (res.test) self.dbService.test.put(res.test)
    if (res.audit) self.dbService.audit.put(res.audit)

    self.ngOnInit()

  }

}
