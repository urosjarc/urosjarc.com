import { TestBed } from '@angular/core/testing';

import { DobiProfilService } from './dobi-profil.service';

describe('DobiProfilService', () => {
  let service: DobiProfilService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DobiProfilService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
