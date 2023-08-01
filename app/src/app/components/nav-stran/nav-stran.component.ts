import {Component, Input} from '@angular/core';
import {NavGumb} from "../nav-gumb/NavGumb";

@Component({
  selector: 'app-nav-stran',
  templateUrl: './nav-stran.component.html',
  styleUrls: ['./nav-stran.component.scss']
})
export class NavStranComponent {
  @Input() navGumbi: NavGumb[] = []
}