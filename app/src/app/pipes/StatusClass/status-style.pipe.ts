import {Pipe, PipeTransform} from '@angular/core';
import {Status} from "../../api/models/status";

@Pipe({
  name: 'StatusStyle',
})
export class StatusStylePipe implements PipeTransform {

  transform(status_tip: Status['tip']): string {
    switch (status_tip) {
      case 'PRAVILNO':
        return "background-color: ForestGreen; color: white"
      case 'NAPACNO':
        return "background-color: orange; color: black"
      case 'NERESENO':
        return "background-color: Red; color: white"
      default:
        return "background-color: white; color: black"
    }
  }

}
