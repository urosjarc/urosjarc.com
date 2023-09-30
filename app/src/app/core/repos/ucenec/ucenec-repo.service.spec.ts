import {UcenecRepoService} from "./ucenec-repo.service";
import {TestBed} from "@angular/core/testing";
import {DbService} from "../../services/db/db.service";
import {osebaData} from "../oseba/db.service.spec.model";


describe('repos: oseba-repo test', () => {
  let osebaRepoService: UcenecRepoService;
  let dbService: DbService;

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
    await dbService.reset(osebaData)
  })

  it('mora inicializirati service', () => {
    expect(osebaRepoService).toBeTruthy();
  });

  it('testi() mora vrniti teste', async () => {
    const result = await osebaRepoService.testi()
    console.log(result, 'testi /******************************************')
    const profil_id = dbService.get_profil_id().toString()
    expect(result).not.toEqual([]);
    // TODO: TA TEST NI DOVOLJ STROG ?
    for (let obj in result[0].test){
      expect(obj).toBeDefined()
    }

    expect(result[0].test.oseba_ucenec_id).toContain(profil_id)
  });

  it('naloga() mora vrniti pravilen id naloge', async () => {
    const testi = await osebaRepoService.testi()
    const naloga_id =testi[0].test.naloga_id[0].toString()
    const result = await osebaRepoService.naloga(naloga_id)
    expect(result?._id).toEqual(naloga_id)
  });

  it('status() mora vrniti status', async () => {
    const testi = await osebaRepoService.testi();
    const test_id = testi[0].test._id.toString();
    const naloga_id =testi[0].test.naloga_id[0].toString()
    console.log(test_id, naloga_id, 'nfeinoiooooooooooooooooooooo***')
    const result = await osebaRepoService.status(test_id, naloga_id)
    expect(result?.test_id).toEqual(test_id);
    expect(result?.naloga_id).toEqual(naloga_id);

  });

})
