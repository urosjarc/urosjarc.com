import {Inject, Injectable} from "@angular/core";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Injectable()
export abstract class WindowModel<T> {
  model: T

  constructor(@Inject(MAT_DIALOG_DATA) model: T) {
    this.model = model
  }
}
