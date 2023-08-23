import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {NgStyle} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {AlertServiceModel} from "../../../core/services/alert/alert.service.model";
import {WindowModel} from "../../../utils/WindowModel";

@Component({
  selector: 'app-prikazi-alert',
  templateUrl: 'prikazi-alert.component.html',
  styleUrls: ['prikazi-alert.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, NgStyle, MatListModule],
})
export class PrikaziAlertComponent extends WindowModel<AlertServiceModel> {}
