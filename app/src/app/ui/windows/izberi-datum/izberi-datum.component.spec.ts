import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzberiDatumComponent } from './izberi-datum.component';

describe('DialogIzberiDatumComponent', () => {
  let component: IzberiDatumComponent;
  let fixture: ComponentFixture<IzberiDatumComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IzberiDatumComponent]
    });
    fixture = TestBed.createComponent(IzberiDatumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
