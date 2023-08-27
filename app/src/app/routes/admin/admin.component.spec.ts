import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminComponent } from './admin.component';
import {CardNavigacijaComponent} from "../../ui/parts/cards/card-navigacija/card-navigacija.component";
import {ActivatedRoute, RouterOutlet} from "@angular/router";
import {OdjaviOseboService} from "../../core/use_cases/odjavi-osebo/odjavi-osebo.service";
import {DbService} from "../../core/services/db/db.service";
import {appUrls} from "../../app.urls";

describe('AdminComponent', () => {
  let fixture: ComponentFixture<AdminComponent>;
  let component: AdminComponent;
  let mockOdjaviOseboService: jasmine.SpyObj<OdjaviOseboService>;

  beforeEach(async () => {
    mockOdjaviOseboService = jasmine.createSpyObj("OdjaviOseboService", ['zdaj']);

    await TestBed.configureTestingModule({
      imports: [AdminComponent, CardNavigacijaComponent, RouterOutlet],
      providers: [
        { provide: OdjaviOseboService, useValue: mockOdjaviOseboService },
        {
          provide: ActivatedRoute, // Provide a mock ActivatedRoute
          useValue: {},
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it( 'mora ustvariti komponento AdminComponent', () => {
    expect(component).toBeTruthy();
  })
  it('funkcija odjava() mora klicati funkcijo zdaj()', () => {
    component.odjava();
    expect(mockOdjaviOseboService.zdaj).toHaveBeenCalled();
  });
  it('mora initializirati gumbe ', () => {
    expect(component.navGumbi.length).toEqual(3);
    }
  )
});
