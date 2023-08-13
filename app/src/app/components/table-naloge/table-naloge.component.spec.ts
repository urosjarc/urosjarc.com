import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableNalogeComponent } from './table-naloge.component';

describe('TableSporocilaComponent', () => {
  let component: TableNalogeComponent;
  let fixture: ComponentFixture<TableNalogeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableNalogeComponent]
    });
    fixture = TestBed.createComponent(TableNalogeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
