import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-form-field-oseba',
  templateUrl: './form-field-oseba.component.html',
  styleUrls: ['./form-field-oseba.component.scss'],
  imports: [
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    NgIf
  ],
  standalone: true
})

export class FormFieldOsebaComponent {
  @Input() label: string = "Ime in priimek"
  @Input() formControl = new FormControl('', [Validators.required,
    Validators.pattern(new RegExp('^[\\p{L}]+(\\s+[\\p{L}]+){1,2}(\\s+)?$', 'um'))

  ]);

  // TODO IMPORTANT: preveri pri UROŠU ČE JE TO OK, POPRAVEK NA 'Vnos nima veljavnih besed'!!!!!!!!!
  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Vnos je obvezen!';
    }

    return 'Vnos nima veljavnih besed!';
  }
}
