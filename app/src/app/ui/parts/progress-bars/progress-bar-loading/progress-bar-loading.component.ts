import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class ProgressBarLoadingComponent {
  @Input() loading: boolean = false;
  @Input() value: number = -1;
}
