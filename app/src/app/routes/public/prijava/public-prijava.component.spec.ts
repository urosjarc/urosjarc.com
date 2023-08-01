import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicPrijavaComponent } from './public-prijava.component';

describe('PrijavaComponent', () => {
  let component: PublicPrijavaComponent;
  let fixture: ComponentFixture<PublicPrijavaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublicPrijavaComponent]
    });
    fixture = TestBed.createComponent(PublicPrijavaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
