import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {StepperSelectionEvent} from "@angular/cdk/stepper";
import {trace} from "../../../utils/trace";
import {MatStepperModule} from "@angular/material/stepper";
import {MatButtonModule} from "@angular/material/button";
import {TableComponent} from "../../../ui/parts/table/table.component";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {SelectionModel} from "@angular/cdk/collections";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {UciteljUcenciModel} from "../ucenci/ucitelj-ucenci.model";
import {
  UciteljZvezkiModel,
  uciteljZvezkiModelMap,
  uciteljZvezkiNalogaModel,
  UciteljZvezkiNalogaModel,
  UciteljZvezkiTematikaModel,
  uciteljZvezkiTematikaModelMap,
  UciteljZvezkiUcenjeModel,
  uciteljZvezkiUcenjeModel
} from "./ucitelj-zvezki.model";

@Component({
  selector: 'app-ucitelj-zvezki',
  templateUrl: './ucitelj-zvezki.component.html',
  styleUrls: ['./ucitelj-zvezki.component.scss'],
  imports: [
    MatStepperModule,
    MatButtonModule,
    TableComponent
  ],
  standalone: true
})
export class UciteljZvezkiComponent implements OnInit {
  @Input() zvezki = new MatTableDataSource<UciteljZvezkiModel>()
  @Input() tematike = new MatTableDataSource<UciteljZvezkiTematikaModel>()
  @Input() naloge = new MatTableDataSource<UciteljZvezkiNalogaModel>()
  @Input() ucenje = new MatTableDataSource<UciteljZvezkiUcenjeModel>()

  zvezki_columns: (keyof UciteljZvezkiModel)[] = ["Tip", "Naslov", "Tematik"];
  tematike_columns: (keyof UciteljZvezkiTematikaModel)[] = ["Zvezek", "Naslov", "Nalog"];
  naloge_columns: (keyof UciteljZvezkiNalogaModel)[] = ["Zvezek", "Tematka", "Resitev", "Vsebina"];
  ucenje_columns: (keyof UciteljUcenciModel)[] = ["Začetek", "Učenec", "Letnik"];

  selectedZvezki = new SelectionModel<UciteljZvezkiModel>(true, []);
  selectedTematike = new SelectionModel<UciteljZvezkiTematikaModel>(true, []);
  selectedNaloge = new SelectionModel<UciteljZvezkiNalogaModel>(true, []);
  selectedUcenje = new SelectionModel<UciteljUcenciModel>(true, []);

  constructor(
    private uciteljRepo: UciteljRepoService,
    private osebaRepo: OsebaRepoService) {
  }

  async ngOnInit() {
    this.zvezki.data = (await this.osebaRepo.zvezki()).map(uciteljZvezkiModelMap)
    this.ucenje.data = (await this.uciteljRepo.ucenje()).map(uciteljZvezkiUcenjeModel)
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
    const tematike: UciteljZvezkiTematikaModel[] = []
    for (const zvezekModel of this.selectedZvezki.selected) {
      tematike.push(...zvezekModel.tematike.map(tematikaModel => uciteljZvezkiTematikaModelMap(tematikaModel, zvezekModel)))
    }
    this.tematike.data = tematike
  }

  @trace()
  private pripraviNaloge() {
    const naloge: UciteljZvezkiNalogaModel[] = []
    for (const tematikaModel of this.selectedTematike.selected) {
      naloge.push(...tematikaModel.naloge.map(nalogaModel => uciteljZvezkiNalogaModel(nalogaModel, tematikaModel)))
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
