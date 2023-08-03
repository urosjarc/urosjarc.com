import {Component, Input, OnDestroy} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription, timer} from 'rxjs';
import {db} from "../../../db";
import {ime} from "../../../utils";
import {AlertService} from "../../../components/alert/alert.service";
import {Location} from '@angular/common';
import {MatTableDataSource} from "@angular/material/table";
import {ApiService} from "../../../api/services/api.service";
import {Naloga} from "../../../api/models/naloga";
import {Status} from "../../../api/models/status";
import {Audit} from "../../../api/models/audit";

@Component({
  selector: 'app-ucenec-naloga',
  templateUrl: './ucenec-naloga.component.html',
  styleUrls: ['./ucenec-naloga.component.scss']
})
export class UcenecNalogaComponent implements OnDestroy {
  test_id: string
  naloga_id: string

  sekunde: number = 0
  stoparica: Subscription;
  naloga: Naloga = {}
  status: Status | undefined
  @Input() audits = new MatTableDataSource<Audit>()
  statusi: Status['tip'][] = [
    'NERESENO',
    'PRAVILNO',
    'NAPACNO',
  ];

  constructor(
    private _location: Location,
    private alertService: AlertService,
    private apiService: ApiService,
    private route: ActivatedRoute) {
    this.test_id = route.snapshot.paramMap.get("test_id") || ""
    this.naloga_id = route.snapshot.paramMap.get("naloga_id") || ""
    this.stoparica = timer(0, 1000)
      .subscribe(val => this.sekunde = val);

    this.initNaloga().then(() => {
      this.initAudits()
    })
  }

  ngOnDestroy() {
    this.stoparica.unsubscribe()
  }

  async initNaloga() {

    const root_id = db.get_root_id()
    const naloga = await db.naloga.where(ime<Naloga>("_id")).equals(this.naloga_id).first()

    if (!naloga) return

    this.naloga = naloga
    this.status = await db.status.where({
      [ime<Status>("naloga_id")]: this.naloga_id,
      [ime<Status>("oseba_id")]: root_id,
      [ime<Status>("test_id")]: this.test_id,
    }).first()
  }

  async initAudits() {
    console.log(this.status)
    this.audits.data = await db.audit.where(ime<Audit>("entitete_id")).equals(this.status?._id || "").toArray()
  }

  nastaviStatus(status_tip: Status['tip']) {
    const self = this;
    this.apiService.ucenecTestTestIdNalogaNalogaIdPost({
      test_id: this.test_id,
      naloga_id: this.naloga_id,
      body: {
        tip: status_tip,
        sekund: this.sekunde,
      }
    }).subscribe({
      next(value) {
        if (value.status) db.status.put(value.status)
        if (value.audit) db.audit.put(value.audit)
        self._location.back()
      }
    })

  }
}
