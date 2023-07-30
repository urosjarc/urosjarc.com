import {Pipe, PipeTransform} from '@angular/core';
import {Date_oddaljenost_v_dneh} from "../../extends/Date";

@Pipe({
  name: 'DateOddaljenost'
})
export class DateOddaljenostPipe implements PipeTransform {

  transform(value: Date): string {
    return `${Date_oddaljenost_v_dneh(value)} dni`;
  }

}
