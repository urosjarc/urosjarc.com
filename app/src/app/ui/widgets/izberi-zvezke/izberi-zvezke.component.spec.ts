import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzberiZvezkeComponent } from './izberi-zvezke.component';

describe('TableSporocilaComponent', () => {
  let component: IzberiZvezkeComponent;
  let fixture: ComponentFixture<IzberiZvezkeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IzberiZvezkeComponent]
    });
    fixture = TestBed.createComponent(IzberiZvezkeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
