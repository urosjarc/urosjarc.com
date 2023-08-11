import {Component, Input} from '@angular/core';
import {NavGumb} from "../nav-gumb/NavGumb";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent {
  @Input() navGumbi: NavGumb[] = []

  onClick() {

  }
}
