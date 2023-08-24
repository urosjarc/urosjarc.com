import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {StepperSelectionEvent} from "@angular/cdk/stepper";
import {ZvezekModel} from "../../../../assets/models/ZvezekModel";
import {TematikaModel} from "../../../../assets/models/TematikaModel";
import {NalogaModel} from "../../../../assets/models/NalogaModel";
import {OsebaModel} from "../../../../assets/models/OsebaModel";
import {trace} from "../../../utils/trace";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {MatStepperModule} from "@angular/material/stepper";
import {TableZvezkiComponent} from "../../../ui/widgets/tables/table-zvezki/table-zvezki.component";
import {TableTematikeComponent} from "../../../ui/widgets/tables/table-tematike/table-tematike.component";
import {TableNalogeComponent} from "../../../ui/widgets/tables/table-naloge/table-naloge.component";
import {TableUcenciComponent} from "../../../ui/widgets/tables/table-ucenci/table-ucenci.component";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-ucitelj-zvezki',
  templateUrl: './ucitelj-zvezki.component.html',
  styleUrls: ['./ucitelj-zvezki.component.scss'],
  imports: [
    MatStepperModule,
    TableZvezkiComponent,
    TableTematikeComponent,
    TableNalogeComponent,
    TableUcenciComponent,
    MatButtonModule
  ],
  standalone: true
})
export class UciteljZvezkiComponent implements OnInit {
  @Input() zvezki: MatTableDataSource<ZvezekModel> = new MatTableDataSource<ZvezekModel>()
  @Input() tematike: MatTableDataSource<TematikaModel> = new MatTableDataSource<TematikaModel>()
  @Input() naloge: MatTableDataSource<NalogaModel> = new MatTableDataSource<NalogaModel>()
  @Input() ucenci: MatTableDataSource<OsebaModel> = new MatTableDataSource<OsebaModel>()

  constructor(private uciteljRepo: UciteljRepoService) {
  }

  async ngOnInit() {
    this.ucenci.data = await this.uciteljRepo.ucenci()
  }

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
    const tematike = []
    for (const zvezek of this.zvezki.data) {
      if (zvezek.izbran) tematike.push(...zvezek.tematike)
    }
    this.tematike.data = tematike
  }

  @trace()
  private pripraviNaloge() {
    const naloge = []
    for (const tematika of this.tematike.data) {
      if (tematika.izbran) naloge.push(...tematika.naloge)
    }
    this.naloge.data = naloge
  }

  @trace()
  private pripraviUcence() {

  }

  @trace()
  private pripraviPotrditev() {

  }
}