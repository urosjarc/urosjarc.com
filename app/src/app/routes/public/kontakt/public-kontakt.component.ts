import {Component} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-public-kontakt',
  templateUrl: './public-kontakt.component.html',
  styleUrls: ['./public-kontakt.component.scss']
})
export class PublicKontaktComponent {
  oseba = new FormControl('', [Validators.required, Validators.pattern("[a-zA-Z]+\\s[a-zA-Z]+")]);
  sporocilo = new FormControl('', [Validators.required, Validators.minLength(10)]);
  telefon = new FormControl('', [Validators.required, Validators.minLength(9)]);
  email = new FormControl('', [Validators.required, Validators.email]);
}
