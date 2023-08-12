import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminIndexComponent } from './admin-index.component';

describe('AdminTestiComponent', () => {
  let component: AdminIndexComponent;
  let fixture: ComponentFixture<AdminIndexComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminIndexComponent]
    });
    fixture = TestBed.createComponent(AdminIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
