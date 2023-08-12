import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UciteljDeloComponent } from './ucitelj-delo.component';

describe('AdminDeloComponent', () => {
  let component: UciteljDeloComponent;
  let fixture: ComponentFixture<UciteljDeloComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UciteljDeloComponent]
    });
    fixture = TestBed.createComponent(UciteljDeloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
