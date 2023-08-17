import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableAuditsComponent } from './table-audits.component';

describe('AuditsTabelaComponent', () => {
  let component: TableAuditsComponent;
  let fixture: ComponentFixture<TableAuditsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableAuditsComponent]
    });
    fixture = TestBed.createComponent(TableAuditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
