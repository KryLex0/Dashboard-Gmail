import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailsStatsComponent } from './emails_stats.component';

describe('EmailsStatsComponent', () => {
  let component: EmailsStatsComponent;
  let fixture: ComponentFixture<EmailsStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmailsStatsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailsStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
