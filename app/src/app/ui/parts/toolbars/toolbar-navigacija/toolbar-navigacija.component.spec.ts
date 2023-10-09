import {ComponentFixture, TestBed} from "@angular/core/testing";
import {ToolbarNavigacijaComponent} from "./toolbar-navigacija.component";
import {ButtonToolbarModel} from "../../buttons/button-toolbar/button-toolbar.model";
import {ActivatedRoute} from "@angular/router";

describe('parts/toolbar-navigacija testi', () => {
  let fixture: ComponentFixture<ToolbarNavigacijaComponent>;
  let component: ToolbarNavigacijaComponent;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[],
      providers: [
        {provide: ActivatedRoute, useValue: {}}
      ]
    }).compileComponents()

    fixture = TestBed.createComponent(ToolbarNavigacijaComponent);
    component = fixture.componentInstance;

  })
  it('mora initializirati komponento', () => {
    expect(component).toBeTruthy();
  })
  it('mora initializirati buttonToolbarModels', () => {
   expect(component.buttonToolbarModels).toEqual([]);
  })
  it('mora pravilno prikazati gumbe', () => {
    component.buttonToolbarModels = [

      {
        tekst: 'gumb1', ikona: '', route: '/', style: '', onClick: () => {
        }
      },
      {
        tekst: 'gumb2', ikona: '', route: '/', style: '', onClick: () => {
        }
      }
    ];
    fixture.detectChanges();
    let appButton = fixture.nativeElement.querySelectorAll('app-button-toolbar')
    expect(appButton.length).toEqual(2);
  })
})
