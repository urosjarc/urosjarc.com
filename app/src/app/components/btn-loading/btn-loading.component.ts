import {Component, Input} from '@angular/core';
import {ThemePalette} from "@angular/material/core";

@Component({
  selector: 'app-btn-loading',
  templateUrl: './btn-loading.component.html',
  styleUrls: ['./btn-loading.component.scss']
})
export class BtnLoadingComponent {

  @Input() loading = false

  @Input() login() {
  }

  @Input() color: ThemePalette = "primary";
  @Input() vsebina: string = "Vsebina";
}
