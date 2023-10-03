import {fakeAsync, TestBed, tick} from '@angular/core/testing';
import {DbService} from './db.service';
import {PromiseExtended} from "dexie";
import {ucenecData} from "./db.service.spec.model";
import {ime} from "../../../utils/types";
import {Oseba} from "../api/models/oseba";

fdescribe('DbService tesitranje', () => {
  let dbService: DbService;


  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DbService],
    });
    dbService = TestBed.inject(DbService);
  });
  afterEach(() => {
    localStorage.clear();
  });

  it('mora biti ustvarjen', () => {
    expect(dbService).toBeTruthy();
  });

  it('mora definirati tabele', () => {

    expect(dbService.oseba).toBeDefined();
    expect(dbService.naslov).toBeDefined();
    expect(dbService.ucenje).toBeDefined();
    expect(dbService.kontakt).toBeDefined();
    expect(dbService.sporocilo).toBeDefined();
    expect(dbService.test).toBeDefined();
    expect(dbService.status).toBeDefined();
    expect(dbService.naloga).toBeDefined();
    expect(dbService.tematika).toBeDefined();
    expect(dbService.zvezek).toBeDefined();
    expect(dbService.audit).toBeDefined();
    expect(dbService.napaka).toBeDefined();

  });

  it('mora vrniti pravilen profile ID iz DB', () => {
    spyOn(localStorage, 'getItem').and.returnValue('id_uporabnika');

    const profileId = dbService.get_profil_id();

    expect(profileId).toEqual('id_uporabnika');
  });

  it('mora nastaviti pravilen profile ID v localStorage', () => {
    const testniProfileId: string = 'uporabniški_id';
    const localStorageSpy = spyOn(localStorage, 'setItem');

    dbService.set_profil_id(testniProfileId);
    expect(localStorageSpy).toHaveBeenCalledWith('root_id', testniProfileId);
  });

  it('mora dobiti token iz localStorage', () => {
    spyOn(localStorage, 'getItem').and.returnValue('123456789s');

    const token = dbService.get_token();

    expect(token).toEqual('123456789s');
  });

  it('mora nastaviti token v localStorage', () => {
    const tokenId: string = '123456789s';
    dbService.set_token(tokenId);
    const shranjeniToken = localStorage.getItem('token')
    expect(shranjeniToken).toEqual(tokenId)
  })
  it('reset() funckija mora vrniti fulfilled promise', async () => {
    const db = await dbService.reset(ucenecData);

    db.forEach((obj, index) => {
      if('status' in obj){
        expect(obj.status).toEqual('fulfilled');
      }
    })

  });
  fit('reset() funckija mora napolniti z pravimi podatki', async () => {
    const promisses = await dbService.reset(ucenecData);
    const profil_id = dbService.get_profil_id();

    // podatki iz osebeData
    const oseba_id = ucenecData.oseba._id;
    const naslov_id = ucenecData.naslov_refs[0].naslov._id;

    // podatki dexie tabele
    const osebe = await dbService.oseba.toArray()
    const naslovi = await dbService.naslov.toArray()

    expect(profil_id).toEqual(oseba_id)
    expect(naslov_id).toEqual(naslovi[0]._id)

  });


});
