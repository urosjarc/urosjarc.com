import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziAuditsOsebeComponent } from './prikazi-audits-osebe.component';

describe('ZgodovinaComponent', () => {
  let component: PrikaziAuditsOsebeComponent;
  let fixture: ComponentFixture<PrikaziAuditsOsebeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziAuditsOsebeComponent]
    });
    fixture = TestBed.createComponent(PrikaziAuditsOsebeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
