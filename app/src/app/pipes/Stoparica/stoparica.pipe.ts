import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'Stoparica'
})
export class StoparicaPipe implements PipeTransform {

  transform(value: number): string {
    const sek = String(Math.round(value % 60)).padStart(2, "0")
    const min = String(Math.round(value / 60)).padStart(2, "0")
    return `${min}:${sek}`
  }
}
