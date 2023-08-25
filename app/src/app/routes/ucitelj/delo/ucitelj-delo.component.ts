import {Component} from '@angular/core';
import {PrikaziAuditsOsebeComponent} from "../../../ui/widgets/prikazi-audits-osebe/prikazi-audits-osebe.component";

@Component({
  selector: 'app-ucitelj-delo',
  templateUrl: './ucitelj-delo.component.html',
  styleUrls: ['./ucitelj-delo.component.scss'],
  imports: [
    PrikaziAuditsOsebeComponent
  ],
  standalone: true
})
export class UciteljDeloComponent {

}
