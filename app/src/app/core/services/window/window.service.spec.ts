import {getTestBed, TestBed} from '@angular/core/testing';
import {MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {WindowService} from './window.service';
import {PublicKontaktComponent} from "../../../routes/public/kontakt/public-kontakt.component";
import {BrowserDynamicTestingModule, platformBrowserDynamicTesting} from "@angular/platform-browser-dynamic/testing";




describe('WindowService', () => {
  let windowService: WindowService;
  let matDialog: MatDialog;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MatDialogModule],
      providers: [WindowService, MatDialog],
    });
    windowService = TestBed.inject(WindowService);
    matDialog = TestBed.inject(MatDialog);
  });

  it('mora odpreti MatDialog z specifično componento in podatki', () => {
    const matDialogOpenSpy = spyOn(matDialog, 'open');
    const sampleComponent = {PublicKontaktComponent} as any;
    const sampleData = {foo: 'bar'};

    const dialogRef = windowService.odpri(sampleComponent, sampleData);

    expect(matDialogOpenSpy).toHaveBeenCalledWith(sampleComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: sampleData,
      disableClose: true,
    });

  });
});
