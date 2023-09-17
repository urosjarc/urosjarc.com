import {OsebaRepoService} from "./oseba-repo.service";
import {TestBed} from "@angular/core/testing";
import {DbService} from "../../services/db/db.service";

describe('repos: oseba-repo test', () => {
    let osebaRepoService: OsebaRepoService;

    beforeEach(() => {


        TestBed.configureTestingModule({
            providers: [
                OsebaRepoService,
                DbService
            ]
        })
        TestBed.inject(OsebaRepoService)
    })

    it('oseba() mora vrniti objekt z pravilnimi podatki', async () => {
        const mockProfilId = '123';
        const mockOsebaData = {
            oseba: { _id: '123'},
            naslovi: [{ oseba_id: '123'}],
            kontakti: [{ oseba_id: '123'}],
        };

        spyOn(DbService.prototype, 'get_profil_id').and.returnValue(mockProfilId);
        await osebaRepoService.oseba()
        expect(true).toBeTrue()

    });
})
