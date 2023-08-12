import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { uciteljGuard } from './ucitelj.guard';

describe('adminGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => uciteljGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
