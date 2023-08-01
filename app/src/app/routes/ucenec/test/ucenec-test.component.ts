import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NalogaInfo} from "./NalogaInfo";
import {db} from "../../../db";
import {Audit, DefaultService, Naloga, Status, Tematika, Test} from "../../../api";
import {ime} from "../../../utils";
import * as moment from "moment";
import {median, standardDeviation} from "simple-statistics";
import {MatDialog} from "@angular/material/dialog";
import {DialogIzberiDatumComponent} from "../../../components/dialog-izberi-datum/dialog-izberi-datum.component";

@Component({
  selector: 'app-ucenec-test',
  templateUrl: './ucenec-test.component.html',
  styleUrls: ['./ucenec-test.component.scss']
})
export class UcenecTestComponent implements OnInit {
  protected readonly Status = Status;
  test_id: string

  statistika_barve: any = {
    domain: ['red', 'orange', '#e1e1e1', 'forestgreen']
  };
  opravljeno_barve: any = {
    domain: ['forestgreen', 'red']
  };

  tema_nalogeInfo = new Map<string, NalogaInfo[]>()
  statistika: { value: number, name: string }[] = [];
  opravljeno: { value: number, name: string }[] = [];
  delo: string = "";
  hitrost: string = "";
  test: string = "";
  deadline: Date = new Date();

  constructor(
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private defaultService: DefaultService
  ) {
    this.test_id = route.snapshot.paramMap.get("test_id") || ""
  }

  async ngOnInit() {
    const root_id = await db.get_root_id()
    const test = await db.test.where({
      [ime<Test>("_id")]: this.test_id,
      [ime<Test>("oseba_ucenec_id")]: root_id
    }).first()

    if (!test) return

    this.deadline = moment(test.deadline).toDate()
    this.initStatistikaOpravljenoDelo(root_id, test)
    this.initCasovnaStatistika(root_id, test)
    this.initNaloge(root_id, test)
  }


  async initCasovnaStatistika(root_id: string, test: Test) {
    const audits = await db.audit.where({
      [ime<Audit>("entitete_id")]: this.test_id,
      [ime<Audit>("tip")]: Audit.tip.STATUS_TIP_POSODOBITEV,
      [ime<Audit>("opis")]: Status.tip.PRAVILNO
    }).toArray()

    let trajanja_sek = audits.map(audit => moment.duration(audit.trajanje).asSeconds())
    let mediana = Math.round(median(trajanja_sek) / 60 * 10) / 10
    let deviacija = Math.round(standardDeviation(trajanja_sek) / 60 * 10) / 10
    let nalog_testa = Math.round(45 / mediana * 10) / 10

    this.test = `${nalog_testa} nalog v 45 minutah`
    this.hitrost = `(${mediana} ± ${deviacija}) minut na nalogo`
  }

  async initStatistikaOpravljenoDelo(root_id: string, test: Test) {
    const statusi = await db.status.where({
      [ime<Status>("oseba_id")]: root_id,
      [ime<Status>("test_id")]: this.test_id,
    }).toArray()

    const statistika_num = new Map<string, number>()
    for (const status of statusi) {
      const status_tip = status.tip || Status.tip.NEZACETO
      const st = statistika_num.get(status_tip) || 0
      statistika_num.set(status_tip, st + 1)
    }
    const pravilno = statistika_num.get(Status.tip.PRAVILNO) || 0
    const vse_naloge = test.naloga_id?.length || 0
    const manjka = vse_naloge - pravilno

    const dni_ostalo = moment(test.deadline).diff(moment(), 'days')
    this.delo = `${dni_ostalo > 0 ? Math.ceil(manjka / dni_ostalo) : manjka} nalog na dan`

    this.opravljeno = [
      {
        name: Status.tip.PRAVILNO,
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

  async initNaloge(root_id: string, test: Test) {
    const naloga_ids = test.naloga_id?.map(ele => ele.toString()) || []

    let i = 0
    const tema_nalogeInfo = new Map<string, NalogaInfo[]>()
    for (const nalogaId of naloga_ids) {
      const naloga = await db.naloga.where(ime<Naloga>("_id")).equals(nalogaId).first()

      if (!naloga) return

      const status = await db.status.where({
        [ime<Status>("naloga_id")]: naloga._id,
        [ime<Status>("oseba_id")]: root_id,
        [ime<Status>("test_id")]: this.test_id,
      }).first()

      const tema = await db.tematika.where(ime<Tematika>("_id")).equals(naloga.tematika_id || "").first()

      if (!tema) continue
      if (!tema.naslov) continue

      const nalogaInfo: NalogaInfo = {
        id: naloga._id || "",
        stevilka: i++,
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

  nastaviDatum() {
    const self = this;
    let matDialogRef = this.dialog.open(DialogIzberiDatumComponent, {
      data: this.deadline,
    });
    matDialogRef.afterClosed().subscribe((datum: Date) => {
      if (datum) {
        let deadline = moment(datum).toISOString(true).split("T")[0]
        this.defaultService.putUcenecTest(this.test_id, {
          datum: deadline
        }).subscribe(
          {
            next(value) {
              if(value.test) db.test.put(value.test)
              if(value.audit) db.audit.put(value.audit)
              self.ngOnInit()
            },
            error(err) {
              console.log("Error: ", err)
            }
          }
        )
      }
    });
  }
}
