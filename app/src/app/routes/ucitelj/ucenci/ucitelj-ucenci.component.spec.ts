import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UciteljUcenciComponent } from './ucitelj-ucenci.component';

describe('AdminUcenciComponent', () => {
  let component: UciteljUcenciComponent;
  let fixture: ComponentFixture<UciteljUcenciComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UciteljUcenciComponent]
    });
    fixture = TestBed.createComponent(UciteljUcenciComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
