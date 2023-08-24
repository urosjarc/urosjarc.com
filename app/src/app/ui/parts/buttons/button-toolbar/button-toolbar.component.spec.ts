import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonToolbarComponent } from './button-toolbar.component';

describe('NavGumbComponent', () => {
  let component: ButtonToolbarComponent;
  let fixture: ComponentFixture<ButtonToolbarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ButtonToolbarComponent]
    });
    fixture = TestBed.createComponent(ButtonToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});