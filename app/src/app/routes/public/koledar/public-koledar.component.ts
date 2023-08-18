import {Component} from '@angular/core';
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-public-koledar',
  templateUrl: './public-koledar.component.html',
  styleUrls: ['./public-koledar.component.scss'],
  standalone: true
})
export class PublicKoledarComponent {

  loading: boolean = true

  @trace()
  loaded() {
    this.loading = false
  }
}
