import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputTelefonComponent } from './input-telefon.component';

describe('InputPhoneComponent', () => {
  let component: InputTelefonComponent;
  let fixture: ComponentFixture<InputTelefonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InputTelefonComponent]
    });
    fixture = TestBed.createComponent(InputTelefonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
