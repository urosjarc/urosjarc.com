import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AlertService} from './alert.service';
import {HttpErrorResponse} from '@angular/common/http';
import {KontaktObrazecRes} from "../api/models/kontakt-obrazec-res";
import {Kontakt} from "../api/models/kontakt";
import {Oseba} from "../api/models/oseba";
import {AlertServiceModel} from "./alert.service.model";
import {ThemePalette} from "@angular/material/core";
import {Subject} from "rxjs";

describe('AlertService', () => {
  let alertService: AlertService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AlertService],
    });
    alertService = TestBed.inject(AlertService);
  });

  it('mora ustvariti componento AlertService', () => {
    expect(alertService).toBeTruthy();
  });

  it('mora prikazati ustrezno sporočilo ob klicu errorServerNiDostopen ', () => {

    const errorResponse = new HttpErrorResponse({
      error: 'Error',
      status: 500,
      statusText: 'Server nedostopen',
    });

    const errorSpy = spyOn<any>(alertService, 'error');
    alertService.errorServerNiDostopen(errorResponse)
    expect(errorSpy).toHaveBeenCalledWith("SERVER NEDOSTOPEN",jasmine.stringMatching(new RegExp(errorResponse.statusText)))

  });
  it('mora prikazati uustrezno sporočilo ob klicu errorServerNapaka', () => {
    const errorResponse = new HttpErrorResponse({
      error: 'Error',
      status: 500,
      statusText: 'Server napaka',
    })

    const errorSpy = spyOn<any>(alertService, 'error');
    alertService.errorServerNapaka(errorResponse);
    expect(errorSpy).toHaveBeenCalledOnceWith('KRITIČNA NAPAKA', jasmine.stringMatching(new RegExp(errorResponse.statusText)))
  });
  //TODO: MOGOČE PREVEČ STRIKTEN TEST??
  it('mora prikazati ustrezno sporočilo ob klicu warnUporabniskaNapaka', () => {
    const errorResponse = new HttpErrorResponse({
      error: {
        info: 'Uporabniška napaka'
      }
    })

    const errorSpy = spyOn<any>(alertService, 'warn');
    alertService.warnUporabniskaNapaka(errorResponse);
    expect(errorSpy).toHaveBeenCalledOnceWith(errorResponse.error.info, jasmine.stringMatching(/Aplikacijo uporabljate na napačen način./))
  });
  //TODO: MOGOČE PREVEČ STRIKTEN TEST??
  it('mora prikazati ustrezno sporočilo ob klicu warnManjkajocaAvtorizacija', () => {

    const errorSpy = spyOn<any>(alertService, 'warn');
    alertService.warnManjkajocaAvtorizacija();
    expect(errorSpy).toHaveBeenCalledOnceWith("Neaktivno avtorizacijsko dovoljenje!", jasmine.stringMatching(/      Vendar nimate aktiviranega avtorizacijskega dovoljenja, da bi lahko nadaljevali./))
  });
  it('mora prikazati ustrezno sporočilo ob klicu infoSprejetnoSporocilo', () => {

    const errorSpy = spyOn<any>(alertService, 'info');
    const mockObrazec: KontaktObrazecRes = {
      email: {} as Kontakt,
      oseba: {} as Oseba,
      sporocila: [],
      telefon: {} as Kontakt,
    };
    alertService.infoSprejetnoSporocilo(mockObrazec);
    expect(errorSpy).toHaveBeenCalledOnceWith("Vaše sporočilo je bilo sprejeto!", jasmine.stringMatching(/Preverite prejem potrditvenih sporočil./))
  });
  it('mora vrniti pravno vsebnost observable ob klicanju get alerts ', (done) => {
    const mockAlert: AlertServiceModel = {
      vsebina: 'To je vsebina',
      naslov: 'To je naslov',
      color:{} as ThemePalette
    };
    const mockSubject = new Subject<AlertServiceModel>();
    // (alertService as any) zato ker je alertsObserver private, ta emita vrednosti vsem subsciberjem
    (alertService as any).alertsObserver = mockSubject;
    alertService.alerts.subscribe((alert) => {
      expect(alert).toEqual(mockAlert);
      done();
    });

    // ko se emita mock alert v mock subject to zazna alerts in dobimo nazaj vrednost, ki jo testiramo
    mockSubject.next(mockAlert);
  });
});
