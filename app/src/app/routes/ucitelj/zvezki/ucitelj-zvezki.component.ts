import {Component} from '@angular/core';
import {SestaviTestComponent} from "../../../ui/widgets/sestavi-test/sestavi-test.component";

@Component({
  selector: 'app-ucitelj-zvezki',
  templateUrl: './ucitelj-zvezki.component.html',
  styleUrls: ['./ucitelj-zvezki.component.scss'],
  imports: [
    SestaviTestComponent
  ],
  standalone: true
})
export class UciteljZvezkiComponent {
}
