import {TestBed} from "@angular/core/testing";
import {UciteljRepoService} from "./ucitelj-repo.service";
import {DbService} from "../../services/db/db.service";
import {uciteljData} from "../../services/db/db.service.spec.model";
import Ajv from "ajv";
import {uciteljTest, uciteljUcenje} from "../oseba/uciteljSchema";


describe('repos: ucitelj-repo.service testi', () => {
  let uciteljRepoService :UciteljRepoService;
  let dbService: DbService;
  const ajv = new Ajv()
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
    await dbService.reset(uciteljData)

  })


  it('mora inicializirati service', () => {
    expect(uciteljRepoService).toBeTruthy();

  });
  it('testi() mora vrniti objekt s pravilno schemo in podatki', async() => {
    const testi  = await uciteljRepoService.testi()
    const profil_id_admin = dbService.get_profil_id();
    const validate = ajv.compile(uciteljTest);
    const valid = validate(testi);

    expect(testi[0].test.oseba_admin_id).toContain(profil_id_admin);
    expect(valid).toBeTrue();

  });

  it('ucenje() mora vrniti objekt s pravilno schemo in podatki', async () => {
    const ucenje = await uciteljRepoService.ucenje()
    const profil_id_ucitelj_id = dbService.get_profil_id();
    const validate = ajv.compile(uciteljUcenje);
    const valid = validate(ucenje);

    expect(ucenje[0].ucenje.oseba_ucitelj_id).toEqual(profil_id_ucitelj_id)
    expect(valid).toBeTrue();

  });
})
