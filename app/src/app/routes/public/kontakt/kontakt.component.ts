import {Component} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-kontakt',
  templateUrl: './kontakt.component.html',
  styleUrls: ['./kontakt.component.scss']
})
export class KontaktComponent {
  oseba = new FormControl('', [Validators.required, Validators.pattern("[a-zA-Z]+\\s[a-zA-Z]+")]);
  sporocilo = new FormControl('', [Validators.required, Validators.minLength(10)]);
  telefon = new FormControl('', [Validators.required, Validators.minLength(9)]);
  email = new FormControl('', [Validators.required, Validators.email]);
}
