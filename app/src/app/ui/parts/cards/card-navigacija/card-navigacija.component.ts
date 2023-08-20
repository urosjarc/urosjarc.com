import {Component, Input} from '@angular/core';
import {ButtonToolbarModel} from "../../buttons/button-toolbar/button-toolbar.model";
import {ButtonToolbarComponent} from "../../buttons/button-toolbar/button-toolbar.component";
import {ToolbarNavigacijaComponent} from "../../toolbars/toolbar-navigacija/toolbar-navigacija.component";

@Component({
  selector: 'app-card-navigacija',
  templateUrl: './card-navigacija.component.html',
  styleUrls: ['./card-navigacija.component.scss'],
  imports: [
    ButtonToolbarComponent,
    ToolbarNavigacijaComponent
  ],
  standalone: true
})
export class CardNavigacijaComponent {
  @Input() buttonToolbarModels: ButtonToolbarModel[] = []
}
