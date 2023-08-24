import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzberiNalogeComponent } from './izberi-naloge.component';

describe('TableSporocilaComponent', () => {
  let component: IzberiNalogeComponent;
  let fixture: ComponentFixture<IzberiNalogeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IzberiNalogeComponent]
    });
    fixture = TestBed.createComponent(IzberiNalogeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
