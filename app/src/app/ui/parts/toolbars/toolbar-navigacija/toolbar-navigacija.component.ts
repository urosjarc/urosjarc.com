import {Component, Input} from '@angular/core';
import {NavGumb} from "../../buttons/button-toolbar/NavGumb";

@Component({
  selector: 'app-toolbar-navigacija',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class ToolbarNavigacijaComponent {
  @Input() navGumbi: NavGumb[] = []

  onClick() {

  }
}
