import { TestBed } from '@angular/core/testing';

import { UcenecRepoService } from './ucenec-repo.service';

describe('UcenecRepoService', () => {
  let service: UcenecRepoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UcenecRepoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
