import {Component} from '@angular/core';
import {trace} from "../../../utils/trace";
import {
  ProgressBarLoadingComponent
} from "../../../ui/parts/progress-bars/progress-bar-loading/progress-bar-loading.component";

@Component({
  selector: 'app-public-koledar',
  templateUrl: './public-koledar.component.html',
  styleUrls: ['./public-koledar.component.scss'],
  imports: [
    ProgressBarLoadingComponent
  ],
  standalone: true
})
export class PublicKoledarComponent {

  loading: boolean = true

  @trace()
  loaded() {
    this.loading = false
  }
}
