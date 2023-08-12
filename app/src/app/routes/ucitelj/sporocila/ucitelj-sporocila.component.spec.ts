import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UciteljSporocilaComponent } from './ucitelj-sporocila.component';

describe('AdminSporocilaComponent', () => {
  let component: UciteljSporocilaComponent;
  let fixture: ComponentFixture<UciteljSporocilaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UciteljSporocilaComponent]
    });
    fixture = TestBed.createComponent(UciteljSporocilaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
