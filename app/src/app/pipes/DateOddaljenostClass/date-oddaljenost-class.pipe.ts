import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'DateOddaljenostClass',
  standalone: true
})
export class DateOddaljenostClassPipe implements PipeTransform {

  transform(value: Date): string {
    const days = moment(value).diff(moment(), 'days');
    if (0 <= days && days < 4) {
      return "danger-pulse"
    } else if (4 <= days && days < 7) {
      return "warn-pulse"
    }
    return ""
  }

}
