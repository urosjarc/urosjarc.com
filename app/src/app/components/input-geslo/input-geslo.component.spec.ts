import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputGesloComponent } from './input-geslo.component';

describe('InputGesloComponent', () => {
  let component: InputGesloComponent;
  let fixture: ComponentFixture<InputGesloComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InputGesloComponent]
    });
    fixture = TestBed.createComponent(InputGesloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
