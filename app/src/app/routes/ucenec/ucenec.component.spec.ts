import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecComponent } from './ucenec.component';

describe('UcenecComponent', () => {
  let component: UcenecComponent;
  let fixture: ComponentFixture<UcenecComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecComponent]
    });
    fixture = TestBed.createComponent(UcenecComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
