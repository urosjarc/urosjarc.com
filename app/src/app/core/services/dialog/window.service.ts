import {Injectable} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ComponentType} from "@angular/cdk/overlay";
import {trace} from "../../../utils/trace";
import {WindowModel} from "../../../utils/WindowModel";

@Injectable({
  providedIn: 'root'
})
export class WindowService {

  constructor(private dialog: MatDialog) {
  }

  @trace()
  odpri<D, T = WindowModel<D>>(component: ComponentType<T>, data: D): MatDialogRef<T> {
    return this.dialog.open<T, D>(component, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: data
    });
  }
}
