import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicIndexComponent } from './public-index.component';

describe('PublicComponent', () => {
  let component: PublicIndexComponent;
  let fixture: ComponentFixture<PublicIndexComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublicIndexComponent]
    });
    fixture = TestBed.createComponent(PublicIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
