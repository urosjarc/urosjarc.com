import {Pipe, PipeTransform} from '@angular/core';
import {Status} from "../../../core/services/api/models/status";

@Pipe({
  name: 'statusTip_style',
  standalone: true
})
export class StatusTipStylePipe implements PipeTransform {

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
