import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputMsgComponent } from './input-msg.component';

describe('InputMsgComponent', () => {
  let component: InputMsgComponent;
  let fixture: ComponentFixture<InputMsgComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InputMsgComponent]
    });
    fixture = TestBed.createComponent(InputMsgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
