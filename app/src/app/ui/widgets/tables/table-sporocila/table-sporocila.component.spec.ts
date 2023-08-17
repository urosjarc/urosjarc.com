import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableSporocilaComponent } from './table-sporocila.component';

describe('TableSporocilaComponent', () => {
  let component: TableSporocilaComponent;
  let fixture: ComponentFixture<TableSporocilaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableSporocilaComponent]
    });
    fixture = TestBed.createComponent(TableSporocilaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
