import {ComponentFixture, TestBed} from "@angular/core/testing";
import {ProgressBarLoadingComponent} from "./progress-bar-loading.component";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {NgClass} from "@angular/common";

describe('Parts/ progress-bar-loading komponenta tesitranje', () => {
  let fixture: ComponentFixture<ProgressBarLoadingComponent>;
  let component: ProgressBarLoadingComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        MatProgressBarModule,
        NgClass
      ],
      providers: []
    }).compileComponents()

    fixture = TestBed.createComponent(ProgressBarLoadingComponent);
    component = fixture.componentInstance;

  })
  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();
  })
  // TODO: ALI TESTIRATI TUDI INPUTE CLASS-A?

  it('mora inicializirati @inpute', () => {
    expect(typeof component.loading).toEqual('boolean');
    expect(typeof component.value).toEqual('number');
  });
})
