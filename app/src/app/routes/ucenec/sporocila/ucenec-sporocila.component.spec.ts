import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecSporocilaComponent } from './ucenec-sporocila.component';

describe('SporocilaComponent', () => {
  let component: UcenecSporocilaComponent;
  let fixture: ComponentFixture<UcenecSporocilaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecSporocilaComponent]
    });
    fixture = TestBed.createComponent(UcenecSporocilaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
