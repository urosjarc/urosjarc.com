import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavGumbComponent } from './nav-gumb.component';

describe('NavGumbComponent', () => {
  let component: NavGumbComponent;
  let fixture: ComponentFixture<NavGumbComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavGumbComponent]
    });
    fixture = TestBed.createComponent(NavGumbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
