import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZgodovinaComponent } from './zgodovina.component';

describe('ZgodovinaComponent', () => {
  let component: ZgodovinaComponent;
  let fixture: ComponentFixture<ZgodovinaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ZgodovinaComponent]
    });
    fixture = TestBed.createComponent(ZgodovinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
