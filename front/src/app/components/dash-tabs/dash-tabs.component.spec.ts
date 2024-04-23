import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashTabsComponent } from './dash-tabs.component';

describe('DashTabsComponent', () => {
  let component: DashTabsComponent;
  let fixture: ComponentFixture<DashTabsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashTabsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
