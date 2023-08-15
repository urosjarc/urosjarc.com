import {Component, Input} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {MatTableDataSource} from "@angular/material/table";
import {ZvezekModel} from "../../../models/ZvezekModel";
import {TematikaModel} from "../../../models/TematikaModel";
import {NalogaModel} from "../../../models/NalogaModel";
import {OsebaModel} from "../../../models/OsebaModel";
import {StepperSelectionEvent} from "@angular/cdk/stepper";
import {trace} from "../../../utils";

@Component({
  selector: 'app-ucitelj-zvezki',
  templateUrl: './ucitelj-zvezki.component.html',
  styleUrls: ['./ucitelj-zvezki.component.scss'],
})
export class UciteljZvezkiComponent {
  firstFormGroup = this._formBuilder.group({
    firstCtrl: ['', Validators.required],
  });
  secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required],
  });
  @Input() zvezki: MatTableDataSource<ZvezekModel> = new MatTableDataSource<ZvezekModel>()
  @Input() tematike: MatTableDataSource<TematikaModel> = new MatTableDataSource<TematikaModel>()
  @Input() naloge: MatTableDataSource<NalogaModel> = new MatTableDataSource<NalogaModel>()
  @Input() ucenci: MatTableDataSource<OsebaModel> = new MatTableDataSource<OsebaModel>()


  constructor(private _formBuilder: FormBuilder) {
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
