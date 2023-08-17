import {Component, Input} from '@angular/core';
import {NavGumb} from "../button-toolbar/NavGumb";

@Component({
  selector: 'app-nav-stran',
  templateUrl: './nav-stran.component.html',
  styleUrls: ['./nav-stran.component.scss'],
  standalone: true
})
export class NavStranComponent {
  @Input() navGumbi: NavGumb[] = []
}
