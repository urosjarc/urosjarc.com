import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicKoledarComponent } from './public-koledar.component';

describe('KoledarComponent', () => {
  let component: PublicKoledarComponent;
  let fixture: ComponentFixture<PublicKoledarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublicKoledarComponent]
    });
    fixture = TestBed.createComponent(PublicKoledarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
