import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzberiTematikeComponent } from './izberi-tematike.component';

describe('TableSporocilaComponent', () => {
  let component: IzberiTematikeComponent;
  let fixture: ComponentFixture<IzberiTematikeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IzberiTematikeComponent]
    });
    fixture = TestBed.createComponent(IzberiTematikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
