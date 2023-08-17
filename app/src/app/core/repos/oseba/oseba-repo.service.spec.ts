import { TestBed } from '@angular/core/testing';

import { OsebaRepoService } from './oseba-repo.service';

describe('OsebaRepoService', () => {
  let service: OsebaRepoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OsebaRepoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
