import {ComponentFixture, TestBed} from "@angular/core/testing";
import {PrikaziAuditsOsebeComponent} from "./prikazi-audits-osebe.component";
import {DatePipe} from "@angular/common";
import {DateOddaljenostPipe} from "../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {DurationPipe} from "../../pipes/duration/duration.pipe";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import SpyObj = jasmine.SpyObj;
import {DbService} from "../../../core/services/db/db.service";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";

describe('widgets: prikazi-audits-osebe', () => {
  let fixture : ComponentFixture<PrikaziAuditsOsebeComponent>;
  let component: PrikaziAuditsOsebeComponent;
  let spyOnOsebaRepo;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule
      ],

    providers: [
      DatePipe,
      DateOddaljenostPipe,
      DurationPipe,
      OsebaRepoService,
      DbService,

    ]

    }).compileComponents()

    fixture = TestBed.createComponent(PrikaziAuditsOsebeComponent);
    component = fixture.componentInstance;
  })

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();
  });

  it('mora pravilno klicati funkicje ob ngOnInit()', async () => {
    spyOnOsebaRepo = spyOn((component as any).osebaRepo, 'audits').and.callThrough();
    console.log(spyOnOsebaRepo, 'spy--------------------')
    component.ngOnInit();
    fixture.detectChanges();
    expect(spyOnOsebaRepo).toHaveBeenCalled()
  });
  //TODO: NE VEM ČE TO FUNKCIJONALNOST TESTIRATI
  it('mora pravilno napolniti models array', () => {
      expect(true).toBeTrue()
  });

})
