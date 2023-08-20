import {Component, Input} from '@angular/core';
import {ButtonToolbarModel} from "../../buttons/button-toolbar/button-toolbar.model";
import {ButtonToolbarComponent} from "../../buttons/button-toolbar/button-toolbar.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-toolbar-navigacija',
  templateUrl: './toolbar-navigacija.component.html',
  styleUrls: ['./toolbar-navigacija.component.scss'],
  imports: [
    ButtonToolbarComponent,
    NgForOf
  ],
  standalone: true
})
export class ToolbarNavigacijaComponent {
  @Input() buttonToolbarModels: ButtonToolbarModel[] = []

  onClick() {

  }
}
