import {Pipe, PipeTransform} from '@angular/core';
import * as moment from "moment";
import {DurationInputArg1} from "moment/moment";

@Pipe({
  name: 'duration',
  standalone: true
})
export class DurationPipe implements PipeTransform {
  transform(value: DurationInputArg1): string {
    return moment.duration(value).humanize();
  }
}
