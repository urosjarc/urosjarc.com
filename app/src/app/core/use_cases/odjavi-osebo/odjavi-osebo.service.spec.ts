import { TestBed } from '@angular/core/testing';

import { OdjaviOseboService } from './odjavi-osebo.service';

describe('IzbrisiUporabniskePodatkeService', () => {
  let service: OdjaviOseboService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OdjaviOseboService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
