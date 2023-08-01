import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecDeloComponent } from './ucenec-delo.component';

describe('ZgodovinaComponent', () => {
  let component: UcenecDeloComponent;
  let fixture: ComponentFixture<UcenecDeloComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecDeloComponent]
    });
    fixture = TestBed.createComponent(UcenecDeloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
