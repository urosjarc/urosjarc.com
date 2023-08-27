import {ComponentFixture, TestBed} from '@angular/core/testing';
import {PublicKoledarComponent} from "./public-koledar.component";
import {
  ProgressBarLoadingComponent
} from "../../../ui/parts/progress-bars/progress-bar-loading/progress-bar-loading.component";

describe('PublicKoledarComponent testi', () => {
  let fixture: ComponentFixture<PublicKoledarComponent>;
  let component: PublicKoledarComponent;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgressBarLoadingComponent]
    }).compileComponents()
    // ustvari komponento za testiranje
    fixture = TestBed.createComponent(PublicKoledarComponent);
    // naredimo dostop to komponente njenih metod in podatkov
    component = fixture.componentInstance;

    fixture.detectChanges();
  })
  it('mora naložiti komponento', () => {
    expect(component).toBeTruthy();
  })
  it('mora spremeniti boolean vrednost pri klicanju funckije loaded()', () => {
    component.loaded();
    expect(component.loading).toBeFalse();
  })
})
