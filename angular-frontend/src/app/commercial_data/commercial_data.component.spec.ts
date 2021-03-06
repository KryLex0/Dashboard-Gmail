import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommercialDataComponent } from './commercial_data.component';

describe('CommercialDataComponent', () => {
  let component: CommercialDataComponent;
  let fixture: ComponentFixture<CommercialDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommercialDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommercialDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
