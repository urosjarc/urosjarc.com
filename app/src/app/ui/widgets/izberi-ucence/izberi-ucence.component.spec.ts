import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzberiUcenceComponent } from './izberi-ucence.component';

describe('TableOsebeComponent', () => {
  let component: IzberiUcenceComponent;
  let fixture: ComponentFixture<IzberiUcenceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IzberiUcenceComponent]
    });
    fixture = TestBed.createComponent(IzberiUcenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
