import { Pipe, PipeTransform } from '@angular/core';
import * as moment from "moment";
import {trace} from "../../utils";

@Pipe({
  name: 'Trajanje'
})
export class TrajanjePipe implements PipeTransform {
  transform(value: string): string {
    return moment.duration(value).humanize();
  }
}
