import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogSporociloComponent } from './dialog-sporocilo.component';

describe('DialogSporociloComponent', () => {
  let component: DialogSporociloComponent;
  let fixture: ComponentFixture<DialogSporociloComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DialogSporociloComponent]
    });
    fixture = TestBed.createComponent(DialogSporociloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
