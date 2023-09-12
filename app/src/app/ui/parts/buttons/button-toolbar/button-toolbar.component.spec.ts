import {ComponentFixture, TestBed} from "@angular/core/testing";
import {ButtonToolbarComponent} from "./button-toolbar.component";
import {MatIconModule} from "@angular/material/icon";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";

describe('Parts / Button-toolbar testi', () => {
  let fixture: ComponentFixture<ButtonToolbarComponent>;
  let component: ButtonToolbarComponent;
  let mockValue = '/nazaj';
  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        MatIconModule,
        MatButtonModule,
      ],
      providers: [
        {provide: ActivatedRoute, useValue: mockValue}
      ]

    }).compileComponents()
    fixture  = TestBed.createComponent(ButtonToolbarComponent);
    component = fixture.componentInstance;
  })

  it('mora inicializirati button-toolbar komponento ', () => {
    expect(component).toBeTruthy();
  });
  it('mora vrniti inpute kot undefined ', () => {
    expect(component.tekst).toBeUndefined();
    expect(component.ikona).toBeUndefined();
    expect(component.route).toBeUndefined();
    expect(component.style).toBeUndefined();

  });
  it('mora vrniti pravi type function ob onClick() metodi', () => {

    expect(typeof component.onClick).toEqual('function');
  });

  it('mora klicati onClick() funckijo', () => {
    const spy = spyOn(component, 'onClick');
    component.onClick();
    expect(spy).toHaveBeenCalled()
  })
})
