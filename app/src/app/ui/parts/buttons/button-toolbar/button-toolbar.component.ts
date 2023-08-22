import {Component, Input} from '@angular/core';
import {MatIconModule} from "@angular/material/icon";
import {RouterLink} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-button-toolbar',
  templateUrl: './button-toolbar.component.html',
  styleUrls: ['./button-toolbar.component.scss'],
  imports: [
    MatIconModule,
    RouterLink,
    MatButtonModule
  ],
  standalone: true
})
export class ButtonToolbarComponent {
  @Input() tekst!: string
  @Input() ikona!: string
  @Input() route?: string
  @Input() style?: string
  @Input() onClick: () => void = () => {
  }
  protected readonly JSON = JSON;
}
