import {Component, Input} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {MatTableDataSource} from "@angular/material/table";
import {ZvezekModel} from "../../../models/ZvezekModel";
import {TematikaModel} from "../../../models/TematikaModel";
import {NalogaModel} from "../../../models/NalogaModel";
import {OsebaModel} from "../../../models/OsebaModel";

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

}
