import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { ucenecGuard } from './ucenec.guard';

describe('ucenecGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => ucenecGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
