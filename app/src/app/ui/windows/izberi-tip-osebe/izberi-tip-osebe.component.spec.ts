import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzberiTipOsebeComponent } from './izberi-tip-osebe.component';

describe('AlertComponent', () => {
  let component: IzberiTipOsebeComponent;
  let fixture: ComponentFixture<IzberiTipOsebeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IzberiTipOsebeComponent]
    });
    fixture = TestBed.createComponent(IzberiTipOsebeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
