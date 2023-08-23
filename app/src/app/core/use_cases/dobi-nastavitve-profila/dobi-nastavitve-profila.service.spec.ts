import { TestBed } from '@angular/core/testing';

import { DobiNastavitveProfilaService } from './dobi-nastavitve-profila.service';

describe('DobiNastavitveProfilaService', () => {
  let service: DobiNastavitveProfilaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DobiNastavitveProfilaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
