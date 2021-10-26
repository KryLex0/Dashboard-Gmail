import { TestBed } from '@angular/core/testing';

import { CommercialDataService } from './commercial_data.service';

describe('CommercialDataService', () => {
  let service: CommercialDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommercialDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
