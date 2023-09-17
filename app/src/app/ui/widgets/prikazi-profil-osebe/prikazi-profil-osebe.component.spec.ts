import {ComponentFixture, TestBed} from "@angular/core/testing";
import {PrikaziProfilOsebeComponent} from "./prikazi-profil-osebe.component";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {DbService} from "../../../core/services/db/db.service";
import {OsebaModel} from "../../../core/domain/OsebaModel";
import {Oseba} from "../../../core/services/api/models/oseba";
import {Naslov} from "../../../core/services/api/models/naslov";
import {Kontakt} from "../../../core/services/api/models/kontakt";

describe('widgets: prikazi-profil-osebe testi', () => {
  let fixture: ComponentFixture<PrikaziProfilOsebeComponent>;
  let component: PrikaziProfilOsebeComponent;
  let mockOsebaModel: any = {}
  let spyOnOsebaRepo: any;
  beforeEach(() => {

    TestBed.configureTestingModule({
      providers: [
        OsebaRepoService,
        DbService
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(PrikaziProfilOsebeComponent);
    component = fixture.componentInstance;
    TestBed.inject(OsebaRepoService)

  })
  afterEach(() => {
    mockOsebaModel = {};
  });
  it('mora initializirati komponento', () => {
    expect(component).toBeTruthy();
  });
  // TODO: BI TUKAJ MORAL UPORABITI mockOsebaModel:OsebaModel
  it('mora napolniti odsebo, naslovi in kontakti po ngOnInit() ', async () => {
    mockOsebaModel = {
      oseba: 'testno ime',
      naslovi: 'testni naslov',
      kontakti: ' testni kontakti',
    };
    spyOn((component as any).osebaRepo, 'oseba').and.returnValue(Promise.resolve(mockOsebaModel));

    fixture.detectChanges();
    // zaradi asynca v ngOnInit() počaka da se async izvede.
    await fixture.whenStable();
    expect(component.oseba).toEqual(mockOsebaModel.oseba);
    expect(component.naslovi).toEqual(mockOsebaModel.naslovi);
    expect(component.kontakti).toEqual(mockOsebaModel.kontakti);
  });
  it('mora prekiniti izvajanje kode če je osebaModel prazen objekt', async() => {
    spyOn((component as any).osebaRepo, 'oseba').and.returnValue(Promise.resolve(mockOsebaModel));
    fixture.detectChanges()
    expect(component.oseba).toBeUndefined()
    expect(component.naslovi).toEqual([])
    expect(component.kontakti).toEqual([])
  });
})
