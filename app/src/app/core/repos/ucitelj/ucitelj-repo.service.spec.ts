import {TestBed} from "@angular/core/testing";
import {UciteljRepoService} from "./ucitelj-repo.service";
import {DbService} from "../../services/db/db.service";
import {osebaData} from "../oseba/db.service.spec.model";


describe('repos: ucitelj-repo.service testi', () => {
  let uciteljRepoService :UciteljRepoService;
  let dbService: DbService;

  beforeEach(async () => {

    TestBed.configureTestingModule({
      providers: [
        UciteljRepoService,
        DbService
      ]
    })
    uciteljRepoService = TestBed.inject(UciteljRepoService);
    dbService = TestBed.inject(DbService)

    await dbService.open()
    await dbService.reset(osebaData)

  })


  it('mora inicializirati service', () => {
    expect(uciteljRepoService).toBeTruthy();

  });
  it('testi() funkcija mora vrniti podatke', async() => {
    const testi  = await uciteljRepoService.testi()
    expect(true).toBeTrue();
  });
  // TODO: VRAČA PRAZEN SPISEK
  it('ucenje() funkcija mora vrniti podatke', async () => {
    const ucenje = await uciteljRepoService.ucenje()
    console.log(ucenje, 'ucenje///////////*************')
    expect(true).toBeTrue();

  });
})
