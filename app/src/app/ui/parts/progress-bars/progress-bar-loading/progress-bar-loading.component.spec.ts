import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressBarLoadingComponent } from './progress-bar-loading.component';

describe('LoadingComponent', () => {
  let component: ProgressBarLoadingComponent;
  let fixture: ComponentFixture<ProgressBarLoadingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgressBarLoadingComponent]
    });
    fixture = TestBed.createComponent(ProgressBarLoadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
