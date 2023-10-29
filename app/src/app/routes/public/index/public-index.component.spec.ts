import {ComponentFixture, TestBed} from '@angular/core/testing';
import {PublicIndexComponent} from "./public-index.component";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {NgForOf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatButtonModule} from "@angular/material/button";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";


describe('PublicIndexComponent tests', () => {

  let fixture: ComponentFixture<PublicIndexComponent>;
  let component: PublicIndexComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NgForOf,
        MatIconModule,
        MatExpansionModule,
        RouterLink,
        MatButtonModule,
        NoopAnimationsModule ],
      providers: [{
        provide: ActivatedRoute,
        useValue: {}

      }]
    }).compileComponents()
    // ustvari komponento za testiranje
    fixture = TestBed.createComponent(PublicIndexComponent);
    // naredimo dostop to komponente njenih metode in podatkov
    component = fixture.componentInstance;

    fixture.detectChanges();

  })

  it('mora naložiti komponento PublicIndexComponent', () => {
    expect(component).toBeTruthy()
  });
  // TODO: PREVEČ STRIKTEN TEST?
  it('mora inicializirati kontakte s pravimi podatki', () => {
    expect(component.kontakti).toEqual([
      {
        tekst: '051-240-885',
        ikona: 'phone',
        route: '/kontakt',
      },
      {
        tekst: 'jar.fmf@gmail.com',
        ikona: 'email',
        route: '/kontakt',
      },
    ]);
  });
  it('mora initializirati infos s pravimi vrstami podatkov', () => {
    // TODO: VEREJTNO JE TEST PREVEČ STRIKTEN, ČE BO DODAN ŠE KAKŠEN NASLOV ALI VSEBINA
    expect(component.infos.length).toBe(4);
    for(const info of component.infos){
      expect(typeof info.naslov).toEqual("string");
    }
    for(const info of component.infos){
      expect(typeof info.vsebina).toEqual('string');
    }
  });
})
