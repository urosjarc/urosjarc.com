import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { publicPrijavaGuard } from './public-prijava.guard';

describe('prijavaGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => publicPrijavaGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
