import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecTestComponent } from './ucenec-test.component';

describe('TestComponent', () => {
  let component: UcenecTestComponent;
  let fixture: ComponentFixture<UcenecTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecTestComponent]
    });
    fixture = TestBed.createComponent(UcenecTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
