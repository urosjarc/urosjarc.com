import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogIzberiDatumComponent } from './dialog-izberi-datum.component';

describe('DialogIzberiDatumComponent', () => {
  let component: DialogIzberiDatumComponent;
  let fixture: ComponentFixture<DialogIzberiDatumComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DialogIzberiDatumComponent]
    });
    fixture = TestBed.createComponent(DialogIzberiDatumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
