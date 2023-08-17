import { TestBed } from '@angular/core/testing';

import { PrijaviUporabnikaService } from './prijavi-uporabnika.service';

describe('PrijaviOseboService', () => {
  let service: PrijaviUporabnikaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrijaviUporabnikaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
