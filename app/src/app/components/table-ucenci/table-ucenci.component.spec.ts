import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableUcenciComponent } from './table-ucenci.component';

describe('TableOsebeComponent', () => {
  let component: TableUcenciComponent;
  let fixture: ComponentFixture<TableUcenciComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableUcenciComponent]
    });
    fixture = TestBed.createComponent(TableUcenciComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
