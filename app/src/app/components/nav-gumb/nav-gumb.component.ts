import {Component, Input} from '@angular/core';
import {ThemePalette} from "@angular/material/core";

@Component({
  selector: 'app-nav-gumb',
  templateUrl: './nav-gumb.component.html',
  styleUrls: ['./nav-gumb.component.scss']
})
export class NavGumbComponent {
  @Input() tekst: string = ""
  @Input() ikona: string = ""
  @Input() route: string = ""
  @Input() style: string | undefined = ""
}
