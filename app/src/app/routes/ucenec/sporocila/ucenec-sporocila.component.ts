import {Component} from '@angular/core';
import {TableSporocilaComponent} from "../../../ui/widgets/tables/table-sporocila/table-sporocila.component";

@Component({
  selector: 'app-ucenec-sporocila',
  templateUrl: './ucenec-sporocila.component.html',
  styleUrls: ['./ucenec-sporocila.component.scss'],
  standalone: true,
  imports: [
    TableSporocilaComponent
  ]
})
export class UcenecSporocilaComponent {
}
