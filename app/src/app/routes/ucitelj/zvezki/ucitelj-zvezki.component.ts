import {Component, OnInit} from '@angular/core';
import {trace} from "../../../utils";
import {ZvezekInfo} from "../../../services/data/ZvezekInfo";
import {DataService} from "../../../services/data/data.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-ucitelj-zvezki',
  templateUrl: './ucitelj-zvezki.component.html',
  styleUrls: ['./ucitelj-zvezki.component.scss'],
})
export class UciteljZvezkiComponent implements OnInit {
  zvezki: ZvezekInfo[] = []

  firstFormGroup = this._formBuilder.group({
    firstCtrl: ['', Validators.required],
  });
  secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required],
  });

  constructor(private data: DataService, private _formBuilder: FormBuilder) {
  }

  @trace()
  async ngOnInit() {
    this.zvezki = await this.data.zvezki()
  }

}
