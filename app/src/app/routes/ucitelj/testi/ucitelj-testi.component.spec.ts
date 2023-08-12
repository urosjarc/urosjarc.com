import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UciteljTestiComponent } from './ucitelj-testi.component';

describe('AdminTestiComponent', () => {
  let component: UciteljTestiComponent;
  let fixture: ComponentFixture<UciteljTestiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UciteljTestiComponent]
    });
    fixture = TestBed.createComponent(UciteljTestiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
