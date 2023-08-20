import {Component, Input} from '@angular/core';
import {MatIconModule} from "@angular/material/icon";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-button-toolbar',
  templateUrl: './button-toolbar.component.html',
  styleUrls: ['./button-toolbar.component.scss'],
  imports: [
    MatIconModule,
    RouterLink
  ],
  standalone: true
})
export class ButtonToolbarComponent {
  @Input() tekst: string = ""
  @Input() ikona: string = ""
  @Input() route: string = ""
  @Input() style: string | undefined = ""
  @Input() onClick: () => void = () => {
  }
}
