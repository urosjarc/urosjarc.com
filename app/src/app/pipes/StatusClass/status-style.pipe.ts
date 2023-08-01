import {Pipe, PipeTransform} from '@angular/core';
import {Status} from "../../api";

@Pipe({
  name: 'StatusStyle',
})
export class StatusStylePipe implements PipeTransform {

  transform(status_tip: Status.tip): string {
    switch (status_tip) {
      case Status.tip.PRAVILNO:
        return "background-color: ForestGreen; color: white"
      case Status.tip.NAPACNO:
        return "background-color: orange; color: black"
      case Status.tip.NERESENO:
        return "background-color: Red; color: white"
      default:
        return "background-color: white; color: black"
    }
  }

}
