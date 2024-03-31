import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignarDietaComponent } from './asignar-dieta.component';

describe('AsignarDietaComponent', () => {
  let component: AsignarDietaComponent;
  let fixture: ComponentFixture<AsignarDietaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsignarDietaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AsignarDietaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
