import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {NgStyle} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {ButtonToolbarModel} from "../../parts/buttons/button-toolbar/button-toolbar.model";
import {IzberiTipOsebeModel} from "./izberi-tip-osebe.model";
import {ToolbarNavigacijaComponent} from "../../parts/toolbars/toolbar-navigacija/toolbar-navigacija.component";

@Component({
  selector: 'app-izberi-tip-osebe',
  templateUrl: 'izberi-tip-osebe.component.html',
  styleUrls: ['izberi-tip-osebe.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, NgStyle, MatListModule, ToolbarNavigacijaComponent],
})
export class IzberiTipOsebeComponent {
  navGumbi: ButtonToolbarModel[] = []

  constructor(
    @Inject(MAT_DIALOG_DATA) public model: IzberiTipOsebeModel) {

    model.tipi.forEach(tip => {
      this.navGumbi.push({
        tekst: tip,
        ikona: "person",
        onClick: async () => {
          model.callback(tip)
        }
      })
    })
  }
}
