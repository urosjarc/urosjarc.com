import { TestBed } from '@angular/core/testing';

import { AdminRepoService } from './admin-repo.service';

describe('AdminRepoService', () => {
  let service: AdminRepoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminRepoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
