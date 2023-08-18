import { TestBed } from '@angular/core/testing';

import { PosljiPublicKontaktniObrazecService } from './poslji-public-kontaktni-obrazec.service';

describe('PosljiPublicKontaktniObrazecService', () => {
  let service: PosljiPublicKontaktniObrazecService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PosljiPublicKontaktniObrazecService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
