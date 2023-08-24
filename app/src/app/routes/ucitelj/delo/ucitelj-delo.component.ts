import {Component} from '@angular/core';
import {PrikaziAuditsComponent} from "../../../ui/widgets/tables/prikazi-audits/prikazi-audits.component";

@Component({
  selector: 'app-ucitelj-delo',
  templateUrl: './ucitelj-delo.component.html',
  styleUrls: ['./ucitelj-delo.component.scss'],
  imports: [
    PrikaziAuditsComponent
  ],
  standalone: true
})
export class UciteljDeloComponent {

}
