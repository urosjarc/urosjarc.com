import {getTestBed, TestBed} from '@angular/core/testing';
import {MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {WindowService} from './window.service';
import {PublicKontaktComponent} from "../../../routes/public/kontakt/public-kontakt.component";
import {AlertService} from "../alert/alert.service";
import {
  PosljiPublicKontaktniObrazecService
} from "../../use_cases/poslji-public-kontaktni-obrazec/poslji-public-kontaktni-obrazec.service";
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";


describe('WindowService', () => {
  let windowService: WindowService;
  let matDialog: MatDialog;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MatDialogModule],
      providers: [
        WindowService,
        MatDialog,
        AlertService,
        PosljiPublicKontaktniObrazecService,
        HttpClientModule,
        HttpClient,
        HttpHandler],
    });
    windowService = TestBed.inject(WindowService);
    matDialog = TestBed.inject(MatDialog);
  });
  // TODO: pri WindowService odpri() metodi, mogoče manjka error handling try catch, če se dialog box ne odpre, zaradi kateregakili razolga//  (npr. če komponenta ne obstaja ali se ne inicializira) vrne samo null...
  it('mora odpreti MatDialog z specifično componento in podatki', () => {

    const matDialogOpenSpy = spyOn(matDialog, 'open').and.callThrough();
    const sampleData = {data: 'sample'};
    const dialogRef = windowService.odpri(PublicKontaktComponent, sampleData);
    expect(matDialogOpenSpy).toHaveBeenCalledWith(PublicKontaktComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: sampleData,
      disableClose: true,
    });
    const dialogContentInstance = dialogRef.componentInstance;
    expect(dialogRef).toBeTruthy();
    expect(dialogContentInstance instanceof PublicKontaktComponent).toBe(true);
  });
});
