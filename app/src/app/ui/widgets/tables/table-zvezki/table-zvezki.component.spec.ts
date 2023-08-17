import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableZvezkiComponent } from './table-zvezki.component';

describe('TableSporocilaComponent', () => {
  let component: TableZvezkiComponent;
  let fixture: ComponentFixture<TableZvezkiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableZvezkiComponent]
    });
    fixture = TestBed.createComponent(TableZvezkiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
