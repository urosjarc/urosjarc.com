import {ComponentFixture, TestBed} from "@angular/core/testing";
import {PublicPrijavaComponent} from "./public-prijava.component";
import {AlertService} from "../../../core/services/alert/alert.service";
import {
    DobiNastavitveProfilaService
} from "../../../core/use_cases/dobi-nastavitve-profila/dobi-nastavitve-profila.service";
import {IzberiTipOsebeService} from "../../../core/use_cases/izberi-tip-osebe/izberi-tip-osebe.service";
import {PrijaviOseboService} from "../../../core/use_cases/prijavi-osebo/prijavi-osebo.service";
import {
    SinhronizirajOsebnePodatkeService
} from "../../../core/use_cases/sinhroniziraj-osebne-podatke/sinhroniziraj-osebne-podatke.service";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {
    NGXLogger,
} from "ngx-logger";
import {LoggerTestingModule} from "ngx-logger/testing";
import {DbService} from "../../../core/services/db/db.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

fdescribe('PublicPrijavaComponent testi', () => {
    let fixture: ComponentFixture<PublicPrijavaComponent>;
    let component: PublicPrijavaComponent;
    beforeEach(async () => {

        await TestBed.configureTestingModule({

            imports: [
                MatDialogModule,
                LoggerTestingModule,
                BrowserAnimationsModule
            ],
            providers: [
                NGXLogger,
                AlertService,
                DobiNastavitveProfilaService,
                IzberiTipOsebeService,
                PrijaviOseboService,
                SinhronizirajOsebnePodatkeService,
                HttpClient,
                HttpHandler,
                MatDialog,
                DbService
            ]
        })

        fixture = TestBed.createComponent(PublicPrijavaComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    })
    fit('should create', () => {
        expect(component).toBeTruthy();
    });

})
