import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {StepperSelectionEvent} from "@angular/cdk/stepper";
import {trace} from "../../../utils/trace";
import {MatStepper, MatStepperModule} from "@angular/material/stepper";
import {MatButtonModule} from "@angular/material/button";
import {TableComponent} from "../../../ui/parts/table/table.component";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {SelectionModel} from "@angular/cdk/collections";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";

import {
  SestaviTestModel,
  SestaviTestNalogaModel,
  SestaviTestTematikaModel,
  SestaviTestUcenjeModel,
  uciteljZvezkiModelMap,
  uciteljZvezkiNalogaModelMap,
  uciteljZvezkiTematikaModelMap,
  uciteljZvezkiUcenjeModelMap
} from "./sestavi-test.model";

@Component({
  selector: 'app-sestavi-test',
  templateUrl: './sestavi-test.component.html',
  styleUrls: ['./sestavi-test.component.scss'],
  imports: [
    MatStepperModule,
    MatButtonModule,
    TableComponent
  ],
  standalone: true
})
export class SestaviTestComponent implements OnInit {
  @Input() zvezki = new MatTableDataSource<SestaviTestModel>()
  @Input() tematike = new MatTableDataSource<SestaviTestTematikaModel>()
  @Input() naloge = new MatTableDataSource<SestaviTestNalogaModel>()
  @Input() ucenje = new MatTableDataSource<SestaviTestUcenjeModel>()

  zvezki_columns: (keyof SestaviTestModel)[] = ["Tip", "Naslov", "Tematik"];
  tematike_columns: (keyof SestaviTestTematikaModel)[] = ["Zvezek", "Naslov", "Nalog"];
  naloge_columns: (keyof SestaviTestNalogaModel)[] = ["Zvezek", "Tematka", "Resitev", "Vsebina"];
  ucenje_columns: (keyof SestaviTestUcenjeModel)[] = ["Začetek", "Učenec", "Letnik"];

  @Input() selectedZvezki = new SelectionModel<SestaviTestModel>(true, []);
  @Input() selectedTematike = new SelectionModel<SestaviTestTematikaModel>(true, []);
  @Input() selectedNaloge = new SelectionModel<SestaviTestNalogaModel>(true, []);
  @Input() selectedUcenje = new SelectionModel<SestaviTestUcenjeModel>(true, []);

  constructor(
    private uciteljRepo: UciteljRepoService,
    private osebaRepo: OsebaRepoService) {
  }

  async ngOnInit() {
    this.zvezki.data = (await this.osebaRepo.zvezki()).map(uciteljZvezkiModelMap)
    this.ucenje.data = (await this.osebaRepo.ucenje()).map(uciteljZvezkiUcenjeModelMap)
  }

  selectionChange($event: StepperSelectionEvent) {

    console.log({
      zvezki: this.selectedZvezki.selected.length,
      tematike: this.selectedTematike.selected.length,
      naloge: this.selectedNaloge.selected.length
    })

    switch ($event.selectedIndex) {
      case 0:
        console.log("Zvezki");
        console.log(this.selectedZvezki.selected)
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
    console.log(this.selectedTematike.selected)
    const tematike: SestaviTestTematikaModel[] = []
    for (const zvezekModel of this.selectedZvezki.selected) {
      tematike.push(...zvezekModel.tematike.map(tematikaModel => uciteljZvezkiTematikaModelMap(tematikaModel, zvezekModel)))
    }
    this.tematike.data = tematike

    //Odstrani tematike ki niso v izbranih zvezkih?
    for(const tematika of tematike){
      if(!this.selectedTematike.isSelected(tematika)){
        this.selectedTematike.deselect(tematika)
      }
    }

  }

  @trace()
  private pripraviNaloge() {
    console.log(this.selectedNaloge.selected)
    const tematike: SestaviTestTematikaModel[] = []
    const naloge: SestaviTestNalogaModel[] = []
    for (const tematikaModel of this.selectedTematike.selected) {
      naloge.push(...tematikaModel.naloge.map(nalogaModel => uciteljZvezkiNalogaModelMap(nalogaModel, tematikaModel)))
    }
    this.naloge.data = naloge

    // Odstrani naloge ki niso v izbranih tematikah?
    for(const naloga of naloge){
      if(!this.selectedNaloge.isSelected(naloga)){
        this.selectedNaloge.deselect(naloga)
      }
    }
  }

  @trace()
  private pripraviUcence() {

  }

  @trace()
  private pripraviPotrditev() {

  }

  reset(stepper: MatStepper) {
    stepper.reset()
    this.selectedNaloge.setSelection()
    this.selectedTematike.setSelection()
    this.selectedZvezki.setSelection()
    this.selectedUcenje.setSelection()
  }
}
