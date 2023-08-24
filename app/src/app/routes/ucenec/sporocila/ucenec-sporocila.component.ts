import {Component} from '@angular/core';
import {PrikaziSporocilaComponent} from "../../../ui/widgets/tables/prikazi-sporocila/prikazi-sporocila.component";

@Component({
  selector: 'app-ucenec-sporocila',
  templateUrl: './ucenec-sporocila.component.html',
  styleUrls: ['./ucenec-sporocila.component.scss'],
  standalone: true,
  imports: [
    PrikaziSporocilaComponent
  ]
})
export class UcenecSporocilaComponent {
}
