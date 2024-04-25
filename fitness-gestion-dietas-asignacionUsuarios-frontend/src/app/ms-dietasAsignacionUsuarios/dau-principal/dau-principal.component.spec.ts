import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DauPrincipalComponent } from './dau-principal.component';

describe('DauPrincipalComponent', () => {
  let component: DauPrincipalComponent;
  let fixture: ComponentFixture<DauPrincipalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DauPrincipalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DauPrincipalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
