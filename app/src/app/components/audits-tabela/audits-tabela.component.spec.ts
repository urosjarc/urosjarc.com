import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditsTabelaComponent } from './audits-tabela.component';

describe('AuditsTabelaComponent', () => {
  let component: AuditsTabelaComponent;
  let fixture: ComponentFixture<AuditsTabelaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditsTabelaComponent]
    });
    fixture = TestBed.createComponent(AuditsTabelaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
