import { TestBed } from '@angular/core/testing';

import { UciteljRepoService } from './ucitelj-repo.service';

describe('UciteljRepoService', () => {
  let service: UciteljRepoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UciteljRepoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
