import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziAuditsComponent } from './prikazi-audits.component';

describe('AuditsTabelaComponent', () => {
  let component: PrikaziAuditsComponent;
  let fixture: ComponentFixture<PrikaziAuditsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziAuditsComponent]
    });
    fixture = TestBed.createComponent(PrikaziAuditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
