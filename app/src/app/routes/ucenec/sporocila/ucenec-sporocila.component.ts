import {Component} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {TableSporocilaComponent} from "../../../ui/widgets/tables/table-sporocila/table-sporocila.component";

@Component({
  selector: 'app-ucenec-sporocila',
  templateUrl: './ucenec-sporocila.component.html',
  styleUrls: ['./ucenec-sporocila.component.scss'],
  standalone: true,
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
  imports: [
    TableSporocilaComponent
  ]
})
export class UcenecSporocilaComponent {
}
