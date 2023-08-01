import {Component, OnDestroy} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription, timer} from 'rxjs';
import {db} from "../../../db";
import {Audit, DefaultService, Naloga, Status} from "../../../api";
import {ime} from "../../../utils";
import {AlertService} from "../../../components/alert/alert.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Location} from '@angular/common';

@Component({
  selector: 'app-naloga',
  templateUrl: './naloga.component.html',
  styleUrls: ['./naloga.component.scss']
})
export class NalogaComponent implements OnDestroy {
  protected readonly Status = Status;
  test_id: string
  naloga_id: string

  sekunde: number = 0
  stoparica: Subscription;
  naloga: Naloga = {}
  status: Status | undefined
  audits: Audit[] = [];
  displayedColumns: string[] = ['opis', 'trajanje', 'ustvarjeno', 'pred'];
  statusi: Status.tip[] = [
    Status.tip.NERESENO,
    Status.tip.PRAVILNO,
    Status.tip.NAPACNO,
  ];

  constructor(
    private _location: Location,
    private alertService: AlertService,
    private defaultService: DefaultService,
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

    const root_id = await db.get_root_id()
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
    this.audits = await db.audit.where(ime<Audit>("entitete_id")).equals(this.status?._id || "").toArray()
    console.log(this.audits)
  }

  nastaviStatus(status_tip: Status.tip) {
    const self = this;
    this.defaultService.postUcenecTestNaloga(this.test_id, this.naloga_id, {
      tip: status_tip,
      sekund: this.sekunde,
    }).subscribe({
      next(value) {
        db.status.put(value)
        self._location.back()
      },
      error(err: HttpErrorResponse) {
        self.alertService.error(err.message)
      }
    })


  }
}
