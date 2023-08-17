import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'sekunde_stoparica',
  standalone: true
})
export class SekundeStoparicaPipe implements PipeTransform {

  transform(sekund: number): string {
    const sek = String(Math.round(sekund % 60)).padStart(2, "0")
    const min = String(Math.round(sekund / 60)).padStart(2, "0")
    return `${min}:${sek}`
  }
}
