import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SporocilaComponent } from './sporocila.component';

describe('SporocilaComponent', () => {
  let component: SporocilaComponent;
  let fixture: ComponentFixture<SporocilaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SporocilaComponent]
    });
    fixture = TestBed.createComponent(SporocilaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
