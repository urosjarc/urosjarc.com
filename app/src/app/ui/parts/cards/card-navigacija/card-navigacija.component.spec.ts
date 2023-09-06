import {ComponentFixture, TestBed} from "@angular/core/testing";
import {CardNavigacijaComponent} from "./card-navigacija.component";
import {ButtonToolbarComponent} from "../../buttons/button-toolbar/button-toolbar.component";
import {ToolbarNavigacijaComponent} from "../../toolbars/toolbar-navigacija/toolbar-navigacija.component";
import {ButtonToolbarModel} from "../../buttons/button-toolbar/button-toolbar.model";

describe('Parts button tests', () => {
  let fixture: ComponentFixture<CardNavigacijaComponent>;
  let component: CardNavigacijaComponent;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        ButtonToolbarComponent,
        ToolbarNavigacijaComponent
      ],
      providers: []

    }).compileComponents()

    // @ts-ignore

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
