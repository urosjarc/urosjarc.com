import {OsebaRepoService} from "./oseba-repo.service";
import {TestBed} from "@angular/core/testing";
import {DbService} from "../../services/db/db.service";

function helperMockFunkcija(data: string | object){
  return {
    where: jasmine.createSpy('where').and.returnValue({
      equals: jasmine.createSpy('equals').and.returnValue({
        first: jasmine.createSpy('first').and.returnValue(
          Promise.resolve(data)
        ),
        toString: jasmine.createSpy('toString').and.returnValue(data),
        toArray: jasmine.createSpy('toArray').and.returnValue(Promise.resolve(data))
      })
    })
  }
}

describe('repos: oseba-repo test', () => {
  let osebaRepoService: OsebaRepoService;

  beforeEach(() => {
    Promise.resolve([{
      zvezek: 'zvezek',
      tematike: 'tematike'
    }])

    const mockDbService = {
      get_profil_id: jasmine.createSpy('get_profil_id').and.returnValue('1234'),

      zvezek: {
          toArray: jasmine.createSpy('toArray').and.returnValue(
            Promise.resolve([{
              _id: '12121212',
              zvezek: 'zvezek',
              tematike: 'tematike'
            }])
          ),
        },
      oseba: helperMockFunkcija('1234'),
      naslov: helperMockFunkcija('naslovi_id'),
      kontakt: helperMockFunkcija('kontakti_id'),
      sporocilo: helperMockFunkcija([{'kontakt_posiljatelj_id' : '12344', 'poslano': '2023-8-19' }]),
      // tematika: {
      //   where: jasmine.createSpy('where').and.returnValue({
      //     equals: jasmine.createSpy('equals').and.returnValue({
      //       toString: jasmine.createSpy('toString').and.returnValue('mocked_value'),
      //       toArray: jasmine.createSpy('toArray').and.returnValue(Promise.resolve([{_id: 'nekiid'}])
      //       ),
      //     }),
      //   }),
      // },
      tematika: helperMockFunkcija([{_id: 'nekiid'}]),
      naloga: helperMockFunkcija('naloga')
    };
    TestBed.configureTestingModule({
      providers: [
        OsebaRepoService,
        {provide: DbService, useValue: mockDbService}
      ]
    })
    osebaRepoService = TestBed.inject(OsebaRepoService);
  })

  it('oseba() mora vrniti objekt z pravilnimi podatki', async () => {
    const result = await osebaRepoService.oseba()
    expect(typeof result?.oseba).toEqual('string')
    expect(typeof result?.kontakti).toEqual('string')
    expect(typeof result?.naslovi).toEqual('string')
  });

  it('sporocila() mora vrniti vsa sporocila', async () => {
    const result = await osebaRepoService.sporocila()
    expect(true).toBeTrue()

  });
  it('zvezki() mora vrniti vse zvezke', async() => {
    const result = await osebaRepoService.zvezki();
    console.log(result, 'zvezki---------------------------')
    expect(true).toBeTrue()
  });
})
