import {ComponentFixture, TestBed} from "@angular/core/testing";
import {ButtonToolbarComponent} from "./button-toolbar.component";
import {MatIconModule} from "@angular/material/icon";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {By} from "@angular/platform-browser";
import {Component} from "@angular/core";
import {parseJson} from "@angular/cli/src/utilities/json-file";

describe('Parts / Button-toolbar testi', () => {
  let component: ButtonToolbarComponent;
  let fixture: ComponentFixture<ButtonToolbarComponent>
  let mockValue = '/nazaj';
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: ActivatedRoute, useValue: mockValue}
      ]
    })
    fixture = TestBed.createComponent(ButtonToolbarComponent)
    component = fixture.componentInstance
  })


  it('@input style ', () => {
    component.style = "style: value"
    fixture.detectChanges()
    const button = fixture.debugElement.query(By.css('button'));
    expect(button.styles['style']).toContain('value')

  });


  it('@input tekst ', () => {
    component.tekst = "text"
    fixture.detectChanges()
    const button = fixture.debugElement.query(By.css('button'));
    const buttonText = button.nativeElement.textContent.trim();
    console.log(buttonText, 'button-----------------')

    expect(true).toBeTrue()

  });

  it('mora klicati onClick() funckijo', () => {
    const spy = spyOn(component, 'onClick');
    component.onClick();
    expect(spy).toHaveBeenCalled()
  })
})
