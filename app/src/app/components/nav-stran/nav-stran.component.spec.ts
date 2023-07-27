import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavStranComponent } from './nav-stran.component';

describe('NavStranComponent', () => {
  let component: NavStranComponent;
  let fixture: ComponentFixture<NavStranComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavStranComponent]
    });
    fixture = TestBed.createComponent(NavStranComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
