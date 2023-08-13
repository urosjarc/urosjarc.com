import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableTematikeComponent } from './table-tematike.component';

describe('TableSporocilaComponent', () => {
  let component: TableTematikeComponent;
  let fixture: ComponentFixture<TableTematikeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableTematikeComponent]
    });
    fixture = TestBed.createComponent(TableTematikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
