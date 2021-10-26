import { TestBed } from '@angular/core/testing';

import { CommercialMailDataService } from './commercial-mail-data.service';

describe('CommercialMailDataService', () => {
  let service: CommercialMailDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommercialMailDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
