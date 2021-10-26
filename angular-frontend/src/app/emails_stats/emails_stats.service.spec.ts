import { TestBed } from '@angular/core/testing';

import { EmailsStatsService } from './emails_stats.service';

describe('EmailsStatsService', () => {
  let service: EmailsStatsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmailsStatsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
