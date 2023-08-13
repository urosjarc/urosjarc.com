import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UciteljZvezkiComponent } from './ucitelj-zvezki.component';

describe('UciteljZvezkiComponent', () => {
  let component: UciteljZvezkiComponent;
  let fixture: ComponentFixture<UciteljZvezkiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UciteljZvezkiComponent]
    });
    fixture = TestBed.createComponent(UciteljZvezkiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
