import {Component} from '@angular/core';
import {DefaultService} from "../../../api";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-prijava',
  templateUrl: './prijava.component.html',
  styleUrls: ['./prijava.component.scss']
})
export class PrijavaComponent {
  uporabnik: FormControl<string | null> = new FormControl('', [Validators.required]);

  constructor(private defaultService: DefaultService) {
  }

  prijava() {
    this.defaultService.postAuthPrijava({
      username: this.uporabnik.getRawValue() || ""
    }).subscribe((res) => {
      console.log(res)
    }, (err) => {
      alert(JSON.stringify(err, null, 4))
    })
  }

}
