import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss'],
  standalone: true
})
export class ProgressBarLoadingComponent {
  @Input() loading: boolean = false;
  @Input() value: number = -1;
}
