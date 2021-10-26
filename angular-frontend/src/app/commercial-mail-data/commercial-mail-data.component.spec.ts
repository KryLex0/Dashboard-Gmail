import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommercialMailDataComponent } from './commercial-mail-data.component';

describe('CommercialMailDataComponent', () => {
  let component: CommercialMailDataComponent;
  let fixture: ComponentFixture<CommercialMailDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommercialMailDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommercialMailDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
