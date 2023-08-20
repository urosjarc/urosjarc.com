import {Component, Input} from '@angular/core';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-progress-bar-loading',
  templateUrl: './progress-bar-loading.component.html',
  styleUrls: ['./progress-bar-loading.component.scss'],
  imports: [
    MatProgressBarModule,
    NgClass
  ],
  standalone: true
})
export class ProgressBarLoadingComponent {
  @Input() loading: boolean = false;
  @Input() value: number = -1;
}
