import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NalogaComponent } from './naloga.component';

describe('NalogaComponent', () => {
  let component: NalogaComponent;
  let fixture: ComponentFixture<NalogaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NalogaComponent]
    });
    fixture = TestBed.createComponent(NalogaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
