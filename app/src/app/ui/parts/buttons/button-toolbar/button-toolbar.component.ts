import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-nav-gumb',
  templateUrl: './nav-gumb.component.html',
  styleUrls: ['./nav-gumb.component.scss'],
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
