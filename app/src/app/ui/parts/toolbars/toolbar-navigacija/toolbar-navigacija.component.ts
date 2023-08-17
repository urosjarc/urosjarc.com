import {Component, Input} from '@angular/core';
import {ButtonToolbarModel} from "../../buttons/button-toolbar/button-toolbar.model";

@Component({
  selector: 'app-toolbar-navigacija',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss'],
  standalone: true
})
export class ToolbarNavigacijaComponent {
  @Input() navGumbi: ButtonToolbarModel[] = []

  onClick() {

  }
}
