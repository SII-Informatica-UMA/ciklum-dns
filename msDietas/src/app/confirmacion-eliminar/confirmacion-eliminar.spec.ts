import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ConfirmacionEliminar } from './confirmacion-eliminar';

describe('FormularioDietaComponent', () => {
  let component: ConfirmacionEliminar;
  let fixture: ComponentFixture<ConfirmacionEliminar>;
  

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmacionEliminar]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfirmacionEliminar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});