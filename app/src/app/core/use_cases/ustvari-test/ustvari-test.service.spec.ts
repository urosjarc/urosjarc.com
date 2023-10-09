import { TestBed } from '@angular/core/testing';

import { UstvariTestService } from './ustvari-test.service';

describe('UstvariTestService', () => {
  let service: UstvariTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UstvariTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
