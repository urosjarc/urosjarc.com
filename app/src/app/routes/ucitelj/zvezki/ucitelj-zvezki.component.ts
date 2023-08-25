import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {StepperSelectionEvent} from "@angular/cdk/stepper";
import {trace} from "../../../utils/trace";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {MatStepperModule} from "@angular/material/stepper";
import {MatButtonModule} from "@angular/material/button";
import {ZvezekModel} from "../../../core/domain/ZvezekModel";
import {TematikaModel} from "../../../core/domain/TematikaModel";
import {NalogaModel} from "../../../core/domain/NalogaModel";
import {OsebaModel} from "../../../core/domain/OsebaModel";

@Component({
  selector: 'app-ucitelj-zvezki',
  templateUrl: './ucitelj-zvezki.component.html',
  styleUrls: ['./ucitelj-zvezki.component.scss'],
  imports: [
    MatStepperModule,
    MatButtonModule
  ],
  standalone: true
})
export class UciteljZvezkiComponent {
  @Input() zvezki: MatTableDataSource<ZvezekModel> = new MatTableDataSource<ZvezekModel>()
  @Input() tematike: MatTableDataSource<TematikaModel> = new MatTableDataSource<TematikaModel>()
  @Input() naloge: MatTableDataSource<NalogaModel> = new MatTableDataSource<NalogaModel>()
  @Input() ucenci: MatTableDataSource<OsebaModel> = new MatTableDataSource<OsebaModel>()

  selectionChange($event: StepperSelectionEvent) {

    switch ($event.selectedIndex) {
      case 0:
        console.log("Zvezki");
        break;
      case 1:
        this.pripraviTematike();
        break;
      case 2:
        this.pripraviNaloge();
        break;
      case 3:
        this.pripraviUcence();
        break;
      case 4:
        this.pripraviPotrditev();
        break;
    }

  }

  @trace()
  private pripraviTematike() {
  }

  @trace()
  private pripraviNaloge() {
  }

  @trace()
  private pripraviUcence() {

  }

  @trace()
  private pripraviPotrditev() {

  }
}
