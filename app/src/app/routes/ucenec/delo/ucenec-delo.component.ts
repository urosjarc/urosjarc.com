import {Component} from '@angular/core';
import {PrikaziAuditsComponent} from "../../../ui/widgets/tables/prikazi-audits/prikazi-audits.component";

@Component({
  selector: 'app-ucenec-delo',
  templateUrl: './ucenec-delo.component.html',
  styleUrls: ['./ucenec-delo.component.scss'],
  imports: [
    PrikaziAuditsComponent
  ],
  standalone: true
})
export class UcenecDeloComponent {
}
