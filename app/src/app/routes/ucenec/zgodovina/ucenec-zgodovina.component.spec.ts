import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecZgodovinaComponent } from './ucenec-zgodovina.component';

describe('ZgodovinaComponent', () => {
  let component: UcenecZgodovinaComponent;
  let fixture: ComponentFixture<UcenecZgodovinaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecZgodovinaComponent]
    });
    fixture = TestBed.createComponent(UcenecZgodovinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
