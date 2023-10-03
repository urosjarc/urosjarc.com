import {UcenecRepoService} from "./ucenec-repo.service";
import {TestBed} from "@angular/core/testing";
import {DbService} from "../../services/db/db.service";
import {ucenecData} from "../../services/db/db.service.spec.model";
import {ucenecNalogaSchema, ucenecStatusSchema, ucenecTestiSchema} from "../oseba/ucenecSchema";
import Ajv from "ajv";

describe('repos: ucenec-repo test', () => {
  let osebaRepoService: UcenecRepoService;
  let dbService: DbService;
  const ajv = new Ajv()
  beforeEach(async () => {


    TestBed.configureTestingModule({
      providers: [
        UcenecRepoService,
        DbService,
      ]
    })
    osebaRepoService = TestBed.inject(UcenecRepoService);
    dbService = TestBed.inject(DbService)
    await dbService.open()
    await dbService.reset(ucenecData)
  })

  it('mora inicializirati service', () => {
    expect(osebaRepoService).toBeTruthy();
  });
  it('testi() mora vrniti objekt s pravilno schemo in podatki', async () => {
    const result = await osebaRepoService.testi()
    const profil_id = dbService.get_profil_id().toString()
    const validate = ajv.compile(ucenecTestiSchema);
    const valid = validate(result);


    expect(result).not.toEqual([]);
    expect(result[0].test.oseba_ucenec_id).toContain(profil_id)
    expect(valid).toBeTrue();
  });

  it('naloga() mora vrniti objekt s pravilno schemo in podatki', async () => {
    const testi = await osebaRepoService.testi()
    const naloga_id =testi[0].test.naloga_id[0].toString()
    const result = await osebaRepoService.naloga(naloga_id)
    const validate = ajv.compile(ucenecNalogaSchema);
    const valid = validate(result);


    expect(result?._id).toEqual(naloga_id)
    expect(valid).toBeTrue();
  });

  it('status() mora vrniti objekt s pravilno schemo in podatki', async () => {

    const testi = await osebaRepoService.testi();
    const test_id = testi[0].test._id.toString();
    const naloga_id =testi[0].test.naloga_id[0].toString()
    const result = await osebaRepoService.status(test_id, naloga_id)
    const validate = ajv.compile(ucenecStatusSchema);
    const valid = validate(result);


    expect(result?.test_id).toEqual(test_id);
    expect(result?.naloga_id).toEqual(naloga_id);
    expect(valid).toBeTrue();

  });

})
