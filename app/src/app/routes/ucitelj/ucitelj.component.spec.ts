import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UciteljComponent } from './ucitelj.component';

describe('AdminComponent', () => {
  let component: UciteljComponent;
  let fixture: ComponentFixture<UciteljComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UciteljComponent]
    });
    fixture = TestBed.createComponent(UciteljComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
