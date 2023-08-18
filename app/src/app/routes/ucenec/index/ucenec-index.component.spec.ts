import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecIndexComponent } from './ucenec-index.component';

describe('UcenecIndexComponent', () => {
  let component: UcenecIndexComponent;
  let fixture: ComponentFixture<UcenecIndexComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UcenecIndexComponent]
    });
    fixture = TestBed.createComponent(UcenecIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
