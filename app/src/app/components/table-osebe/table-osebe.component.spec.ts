import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableOsebeComponent } from './table-osebe.component';

describe('TableOsebeComponent', () => {
  let component: TableOsebeComponent;
  let fixture: ComponentFixture<TableOsebeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableOsebeComponent]
    });
    fixture = TestBed.createComponent(TableOsebeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
