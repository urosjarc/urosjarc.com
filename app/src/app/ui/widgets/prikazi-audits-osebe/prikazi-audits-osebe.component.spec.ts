import {ComponentFixture, TestBed} from "@angular/core/testing";
import {PrikaziAuditsOsebeComponent} from "./prikazi-audits-osebe.component";
import {DatePipe} from "@angular/common";
import {DateOddaljenostPipe} from "../../pipes/date-oddaljenost/date-oddaljenost.pipe";
import {DurationPipe} from "../../pipes/duration/duration.pipe";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {DbService} from "../../../core/services/db/db.service";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {ActivatedRoute} from "@angular/router";

describe('widgets: prikazi-audits-osebe', () => {
  let fixture: ComponentFixture<PrikaziAuditsOsebeComponent>;
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

        {provide: ActivatedRoute, useValue:'/'}

      ]

    }).compileComponents()

    fixture = TestBed.createComponent(PrikaziAuditsOsebeComponent);
    component = fixture.componentInstance;
    TestBed.inject(OsebaRepoService);
  })

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();
  });

  it('mora pravilno klicati funkicje ob ngOnInit()', async () => {
    spyOnOsebaRepo = spyOn((component as any).osebaRepo, 'audits').and.callThrough();
    fixture.detectChanges();
    expect(spyOnOsebaRepo).toHaveBeenCalled()
  });
  //TODO: TUKAJ SEM NAMESTO ustvarjeno: '2023-09-16T10:00:00', NAPOLNIL Z {ustvarjeno: new Date(2023-09-16T10:00:00)}, in se component.audits.data ni napolnil, brez vrnjenega errorja ?
  it('mora pravilno napolniti audits.data array', async () => {
    const mockAudits = [
      {
        tip: 'testni tip 1',
        opis: 'testni opis 1',
        trajanje: '1000',
        ustvarjeno: '2023-09-16T10:00:00',
        'Pred...': '2023-09-16T10:00:00'
      },
      {
        tip: 'testni tip 2',
        opis: 'testni opis 2',
        trajanje: '2000',
        ustvarjeno: '2023-09-16T10:00:00',
        'Pred...': '2023-09-16T10:00:00'
      }
    ];
    spyOn((component as any).osebaRepo, 'audits').and.returnValue(Promise.resolve(mockAudits))
    fixture.detectChanges();
    await fixture.whenStable();

    expect(component.audits.data.length).toEqual(2);
  });

})
