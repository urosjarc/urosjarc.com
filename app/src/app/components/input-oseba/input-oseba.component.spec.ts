import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputOsebaComponent } from './input-oseba.component';

describe('InputOsebaComponent', () => {
  let component: InputOsebaComponent;
  let fixture: ComponentFixture<InputOsebaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InputOsebaComponent]
    });
    fixture = TestBed.createComponent(InputOsebaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
