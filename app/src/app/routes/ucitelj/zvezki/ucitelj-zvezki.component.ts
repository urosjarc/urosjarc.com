import {Component, Input, OnInit} from '@angular/core';
import {trace} from "../../../utils";
import {DataService} from "../../../services/data/data.service";
import {FormBuilder, Validators} from "@angular/forms";
import {MatTableDataSource} from "@angular/material/table";
import {Naloga} from "../../../services/api/openapi/models/naloga";
import {Zvezek} from "../../../services/api/openapi/models/zvezek";
import {Tematika} from "../../../services/api/openapi/models/tematika";
import {Oseba} from "../../../services/api/openapi/models/oseba";
import {OsebaInfo} from "../../../services/data/OsebaInfo";

@Component({
  selector: 'app-ucitelj-zvezki',
  templateUrl: './ucitelj-zvezki.component.html',
  styleUrls: ['./ucitelj-zvezki.component.scss'],
})
export class UciteljZvezkiComponent implements OnInit {
  firstFormGroup = this._formBuilder.group({
    firstCtrl: ['', Validators.required],
  });
  secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required],
  });
  @Input() zvezki: MatTableDataSource<Zvezek> = new MatTableDataSource<Zvezek>()
  @Input() tematike: MatTableDataSource<Tematika> = new MatTableDataSource<Tematika>()
  @Input() naloge: MatTableDataSource<Naloga> = new MatTableDataSource<Naloga>()
  @Input() ucenci: MatTableDataSource<OsebaInfo> = new MatTableDataSource<OsebaInfo>()

  constructor(private data: DataService, private _formBuilder: FormBuilder) {
  }

  @trace()
  async ngOnInit() {
    const self = this
    const _zvezki: Zvezek[] = []
    const _tematike: Tematika[] = []
    const _naloge: Naloga[] = []

    this.data.zvezki().then(zvezki => {
      zvezki.forEach(zvezek => {
        _zvezki.push(zvezek)
        zvezek.tematike.forEach(tematika => {
          _tematike.push(tematika)
          tematika.naloge.forEach(naloga => {
            _naloge.push(naloga)
          })
        })
      })

      self.zvezki.data = _zvezki
      self.tematike.data = _tematike
      self.naloge.data = _naloge

    })

  }

}
