import {Component, Input, OnDestroy} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription, timer} from 'rxjs';
import {ime} from "../../../utils";
import {AlertService} from "../../../services/alert/alert.service";
import {Location} from '@angular/common';
import {MatTableDataSource} from "@angular/material/table";
import {Naloga} from "../../../services/api/openapi/models/naloga";
import {Status} from "../../../services/api/openapi/models/status";
import {Audit} from "../../../services/api/openapi/models/audit";
import {ApiService} from "../../../services/api/openapi/services/api.service";
import {DbService} from "../../../services/db/db.service";

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
    private dbService: DbService,
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

    const root_id = this.dbService.get_root_id()
    const naloga = await this.dbService.naloga.where(ime<Naloga>("_id")).equals(this.naloga_id).first()

    if (!naloga) return

    this.naloga = naloga
    this.status = await this.dbService.status.where({
      [ime<Status>("naloga_id")]: this.naloga_id,
      [ime<Status>("oseba_id")]: root_id,
      [ime<Status>("test_id")]: this.test_id,
    }).first()
  }

  async initAudits() {
    this.audits.data = await this.dbService.audit.where(ime<Audit>("entitete_id")).equals(this.status?._id || "").toArray()
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
        if (value.status) self.dbService.status.put(value.status)
        if (value.audit) self.dbService.audit.put(value.audit)
        self._location.back()
      }
    })

  }
}
