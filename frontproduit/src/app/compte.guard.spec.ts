import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { compteGuard } from './compte.guard';

describe('compteGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => compteGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
