import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToolbarNavigacijaComponent } from './toolbar-navigacija.component';

describe('NavBarComponent', () => {
  let component: ToolbarNavigacijaComponent;
  let fixture: ComponentFixture<ToolbarNavigacijaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ToolbarNavigacijaComponent]
    });
    fixture = TestBed.createComponent(ToolbarNavigacijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
