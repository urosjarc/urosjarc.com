import { TestBed } from '@angular/core/testing';

import { PrijaviOseboService } from './prijavi-osebo.service';

describe('PrijaviOseboService', () => {
  let service: PrijaviOseboService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrijaviOseboService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
