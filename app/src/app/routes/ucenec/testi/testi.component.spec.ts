import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestiComponent } from './testi.component';

describe('TestiComponent', () => {
  let component: TestiComponent;
  let fixture: ComponentFixture<TestiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TestiComponent]
    });
    fixture = TestBed.createComponent(TestiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
