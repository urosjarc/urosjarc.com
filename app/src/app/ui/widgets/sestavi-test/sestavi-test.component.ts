import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
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
  SestaviTestNalogaModel,
  SestaviTestTematikaModel,
  SestaviTestUcenjeModel,
  sestaviTestUcenjeModelMap,
  SestaviTestZvezekModel,
  sestaviTestZvezekModelMap,
} from "./sestavi-test-zvezek.model";
import {DatePipe, NgForOf} from "@angular/common";
import {StatusTipStylePipe} from "../../pipes/statusTip-style/statusTip-style.pipe";
import {MatListModule} from "@angular/material/list";
import {UstvariTestService} from "../../../core/use_cases/ustvari-test/ustvari-test.service";
import {
  ProgressBarLoadingComponent
} from "../../parts/progress-bars/progress-bar-loading/progress-bar-loading.component";
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FormFieldBesedeComponent} from "../../parts/form-fields/form-field-besede/form-field-besede.component";
import {MatInputModule} from "@angular/material/input";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {Date_datumStr} from "../../../utils/Date";
import {FormFieldDatumComponent} from "../../parts/form-fields/form-field-datum/form-field-datum.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sestavi-test',
  templateUrl: './sestavi-test.component.html',
  styleUrls: ['./sestavi-test.component.scss'],
  imports: [
    MatStepperModule,
    MatButtonModule,
    TableComponent,
    NgForOf,
    StatusTipStylePipe,
    MatListModule,
    ProgressBarLoadingComponent,
    ReactiveFormsModule,
    FormFieldBesedeComponent,
    MatInputModule,
    MatDatepickerModule,
    FormFieldDatumComponent
  ],
  standalone: true,
  encapsulation: ViewEncapsulation.None
})
export class SestaviTestComponent implements OnInit {
  @Input() onFinish: string = "/"
  @Input() zvezki = new MatTableDataSource<SestaviTestZvezekModel>()
  tematike = new MatTableDataSource<SestaviTestTematikaModel>()
  naloge = new MatTableDataSource<SestaviTestNalogaModel>()
  ucenje = new MatTableDataSource<SestaviTestUcenjeModel>()

  zvezki_columns: (keyof SestaviTestZvezekModel)[] = ["Tip", "Naslov", "Tematik"];
  tematike_columns: (keyof SestaviTestTematikaModel)[] = ["Zvezek", "Naslov", "Nalog"];
  naloge_columns: (keyof SestaviTestNalogaModel)[] = ["Zvezek", "Tematka", "Resitev", "Vsebina"];
  ucenje_columns: (keyof SestaviTestUcenjeModel)[] = ["Začetek", "Učenec", "Letnik"];

  selectedZvezki = new SelectionModel<SestaviTestZvezekModel>(true, []);
  selectedTematike = new SelectionModel<SestaviTestTematikaModel>(true, []);
  @Input() selectedNaloge = new SelectionModel<SestaviTestNalogaModel>(true, []);
  @Input() selectedUcenje = new SelectionModel<SestaviTestUcenjeModel>(true, []);

  @ViewChild('naslov') formFieldNaslovComponent?: FormFieldBesedeComponent;
  @ViewChild('podnaslov') formFieldPodnaslovComponent?: FormFieldBesedeComponent;
  @ViewChild('deadline') formFieldDeadlineComponent?: FormFieldDatumComponent;

  formGroup: FormGroup = new FormGroup({});
  loading: boolean = false;

  constructor(
    private router: Router,
    private datePipe: DatePipe,
    private ustvariTest: UstvariTestService,
    private uciteljRepo: UciteljRepoService,
    private osebaRepo: OsebaRepoService) {
  }

  async ngOnInit() {
    this.zvezki.data = (await this.osebaRepo.zvezki()).map(sestaviTestZvezekModelMap)
    this.ucenje.data = (await this.osebaRepo.ucenje()).map(ele => sestaviTestUcenjeModelMap(this.datePipe, ele))
  }

  @trace()
  ngAfterViewInit(): void {
    this.formGroup = new FormGroup({//@ts-ignore
      podnaslov: this.formFieldPodnaslovComponent?.formControl,//@ts-ignore
      naslov: this.formFieldNaslovComponent?.formControl,//@ts-ignore
      deadline: this.formFieldDeadlineComponent?.formControl
    });
  }

  selectionChange($event: StepperSelectionEvent) {
    switch ($event.selectedIndex) {
      case 0:
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
    const tematike: SestaviTestTematikaModel[] = []
    for (const zvezek of this.selectedZvezki.selected) {
      tematike.push(...zvezek.tematikeModels)
    }
    for (const tematika of this.selectedTematike.selected) {
      if (!tematike.includes(tematika)) this.selectedTematike.deselect(tematika)
    }
    this.tematike.data = tematike
  }

  @trace()
  private pripraviNaloge() {
    const naloge: SestaviTestNalogaModel[] = []
    for (const tematika of this.selectedTematike.selected) {
      naloge.push(...tematika.nalogaModels)
    }
    for (const naloga of this.selectedNaloge.selected) {
      if (!naloge.includes(naloga)) this.selectedNaloge.deselect(naloga)
    }
    this.naloge.data = naloge
  }

  @trace()
  private pripraviUcence() {

  }

  @trace()
  private pripraviPotrditev() {
    this.pripraviTematike()
    this.pripraviNaloge()
  }

  reset(stepper: MatStepper) {
    stepper.reset()
    this.selectedNaloge.setSelection()
    this.selectedTematike.setSelection()
    this.selectedZvezki.setSelection()
    this.selectedUcenje.setSelection()
  }

  async potrdi() {
    if (this.formFieldDeadlineComponent?.formControl.value == null) return

    const test = await this.ustvariTest.zdaj({
      deadline: Date_datumStr(this.formFieldDeadlineComponent?.formControl.value, true),
      naslov: this.formFieldNaslovComponent?.formControl.value || "",
      podnaslov: this.formFieldPodnaslovComponent?.formControl.value || "",
      oseba_admini_id: [],
      oseba_ucenci_id: this.selectedUcenje.selected.map(ele => ele.oseba._id),
      naloga_id: this.selectedNaloge.selected.map(ele => ele.naloga._id),
    })

    if (test) await this.router.navigate([this.onFinish])
  }

}
