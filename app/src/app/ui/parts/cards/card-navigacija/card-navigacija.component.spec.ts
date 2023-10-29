import {ComponentFixture, TestBed} from "@angular/core/testing";
import {CardNavigacijaComponent} from "./card-navigacija.component";

describe('Parts  button tests', () => {
  let fixture : ComponentFixture<CardNavigacijaComponent>;
  let component: CardNavigacijaComponent;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [],
      providers: [
        CardNavigacijaComponent
      ]

    }).compileComponents()
    fixture = TestBed.createComponent(CardNavigacijaComponent);
    component = fixture.componentInstance;
  })

  it('mora inicializirati card-navigacija komponento ', () => {
    expect(component).toBeTruthy();
  });
  it('mora inicializirati prazen spisek buttonToolbarModel z pravimi tipi', () => {
    expect(component.buttonToolbarModels.every(item =>
      typeof item.tekst === 'string' &&
      typeof item.ikona === 'string' &&
      typeof item.route === 'string' &&
      typeof item.style === 'string'
    )).toBe(true);
  });
})
