import { TestBed } from '@angular/core/testing';

import { IzbrisiUporabniskePodatkeService } from './izbrisi-uporabniske-podatke.service';

describe('IzbrisiUporabniskePodatkeService', () => {
  let service: IzbrisiUporabniskePodatkeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IzbrisiUporabniskePodatkeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
