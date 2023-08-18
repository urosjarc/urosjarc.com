import {Component, Input, OnDestroy} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription, timer} from 'rxjs';
import {Location} from '@angular/common';
import {MatTableDataSource} from "@angular/material/table";
import {Naloga} from "../../../../../core/services/api/models/naloga";
import {Status} from "../../../../../core/services/api/models/status";
import {Audit} from "../../../../../core/services/api/models/audit";
import {DbService} from "../../../../../core/services/db/db.service";
import {AlertService} from "../../../../../core/services/alert/alert.service";
import {ApiService} from "../../../../../core/services/api/services/api.service";
import {trace} from "../../../../../utils/trace";
import {ime} from "../../../../../utils/types";
import {UcenecRepoService} from "../../../../../core/repos/ucenec/ucenec-repo.service";

@Component({
  selector: 'app-ucenec-naloga',
  templateUrl: './ucenec-naloga.component.html',
  styleUrls: ['./ucenec-naloga.component.scss'],
  standalone: true
})
export class UcenecTestiTestNalogaComponent implements OnDestroy {
  test_id: string
  naloga_id: string

  sekunde: number = 0
  stoparica: Subscription;
  // @ts-ignore
  naloga: Naloga = {}
  status: Status | undefined
  @Input() audits = new MatTableDataSource<Audit>()
  statusi: Status['tip'][] = [
    'NERESENO',
    'NAPACNO',
    'PRAVILNO',
  ];

  constructor(
    private ucenecRepo: UcenecRepoService,
    private _location: Location,
    private dbService: DbService,
    private alertService: AlertService,
    private apiService: ApiService,
    private route: ActivatedRoute) {

    this.test_id = route.snapshot.paramMap.get("test_id") || ""
    this.naloga_id = route.snapshot.paramMap.get("naloga_id") || ""
    this.stoparica = timer(0, 1000).subscribe(val => this.sekunde = val);

    this.initNaloga().then(this.initAudits)
  }

  @trace()
  ngOnDestroy() {
    this.stoparica.unsubscribe()
  }

  @trace()
  async initNaloga() {

    const naloga = await this.ucenecRepo.naloga(this.naloga_id)

    if (!naloga) return

    const status = await this.ucenecRepo.status(this.test_id, this.naloga_id)


  }

  @trace()
  async initAudits() {
  }

  @trace()
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
