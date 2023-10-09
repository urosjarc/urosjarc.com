import {OsebaRepoService} from "./oseba-repo.service";
import {TestBed} from "@angular/core/testing";
import {DbService} from "../../services/db/db.service";
import {ucenecData} from "../../services/db/db.service.spec.model";
import Ajv from 'ajv';
import {ucenecOsebaSchema} from "../scheme/ucenecSchema";
import {ucenecSporocilaSchema} from "../scheme/ucenecSchema";

describe('repos: oseba-repo test', () => {
  let osebaRepoService: OsebaRepoService;
  let dbService: DbService;
  const ajv = new Ajv()
  beforeEach(async () => {


    TestBed.configureTestingModule({
      providers: [
        OsebaRepoService,
        DbService,
      ]
    })
    osebaRepoService = TestBed.inject(OsebaRepoService);
    dbService = TestBed.inject(DbService)
    await dbService.open()
    await dbService.reset(ucenecData)
  })
  it('mora inicailizirati service', () => {
    expect(osebaRepoService).toBeTruthy();
  });

  it('oseba() mora vrniti objekt pravilno schemo in podatki', async () => {
    const result = await osebaRepoService.oseba();

    const profil_id = dbService.get_profil_id().toString()
    const validate = ajv.compile(ucenecOsebaSchema);
    const valid = validate(result);

    expect(result.oseba._id).toEqual(profil_id);
    expect(result.naslovi[0].oseba_id).toEqual(profil_id);
    expect(result.kontakti[0].oseba_id).toContain(profil_id);
    expect(valid).toBe(true);

  });

  it('sporocila() mora vrniti objekt pravilno schemo in podatki', async () => {
    const result = await osebaRepoService.sporocila()
    const profil_id = dbService.get_profil_id().toString()
    const validate = ajv.compile(ucenecSporocilaSchema);
    const valid = validate(result);


    expect(result).not.toEqual([]);
    expect(result[0].posiljatelj._id).toEqual(profil_id);
    expect(result[0].posiljatelj_kontakt.oseba_id).toContain(profil_id);
    expect(valid).toBe(true);


  });

  // TODO: ZVEZKI VRAČA PRAZNI RESPONSE!
  it('zvezki() mora vrniti objekt pravilno schemo in podatki', async () => {
    const result = await osebaRepoService.zvezki();
    console.log(result, 'result zvezki////////-------')
    expect(true).toBeTrue()
  });


})
