import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableTestiComponent } from './table-testi.component';

describe('TestiComponentComponent', () => {
  let component: TableTestiComponent;
  let fixture: ComponentFixture<TableTestiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableTestiComponent]
    });
    fixture = TestBed.createComponent(TableTestiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
