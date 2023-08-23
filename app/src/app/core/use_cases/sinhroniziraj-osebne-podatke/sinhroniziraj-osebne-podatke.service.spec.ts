import { TestBed } from '@angular/core/testing';

import { SinhronizirajOsebnePodatkeService } from './sinhroniziraj-osebne-podatke.service';

describe('SinhronizirajUporabniskePodatkeService', () => {
  let service: SinhronizirajOsebnePodatkeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SinhronizirajOsebnePodatkeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
