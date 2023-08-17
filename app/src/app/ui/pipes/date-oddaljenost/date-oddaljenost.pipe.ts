import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'date_oddaljenost',
  standalone: true
})
export class DateOddaljenostPipe implements PipeTransform {

  transform(value: Date): string {
    return moment(value).fromNow();
  }

}
