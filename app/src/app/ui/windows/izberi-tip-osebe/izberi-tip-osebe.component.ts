import {Component, Inject, Input} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {NgStyle} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {ButtonToolbarModel} from "../../parts/buttons/button-toolbar/button-toolbar.model";
import {IzberiTipOsebeModel} from "./izberi-tip-osebe.model";
import {ToolbarNavigacijaComponent} from "../../parts/toolbars/toolbar-navigacija/toolbar-navigacija.component";
import {
  ProgressBarLoadingComponent
} from "../../parts/progress-bars/progress-bar-loading/progress-bar-loading.component";

@Component({
  selector: 'app-izberi-tip-osebe',
  templateUrl: 'izberi-tip-osebe.component.html',
  styleUrls: ['izberi-tip-osebe.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, NgStyle, MatListModule, ToolbarNavigacijaComponent, ProgressBarLoadingComponent],
})
export class IzberiTipOsebeComponent {
  buttonToolbarModels: ButtonToolbarModel[] = []
  loading: boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public model: IzberiTipOsebeModel) {

    model.tipi.forEach(tip => {
      this.buttonToolbarModels.push({
        tekst: tip,
        ikona: "person",
        onClick: async () => {
          this.loading = true
          console.log(`Izbran je bil: ${tip} ${model.callback}`)
          model.callback(tip)
        }
      })
    })
  }
}
