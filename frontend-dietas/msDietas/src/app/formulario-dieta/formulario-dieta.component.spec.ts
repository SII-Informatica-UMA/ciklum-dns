import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioDietaComponent } from './formulario-dieta.component';

import { FormsModule } from '@angular/forms';

describe('FormularioDietaComponent', () => {
  let component: FormularioDietaComponent;
  let fixture: ComponentFixture<FormularioDietaComponent>;
  

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioDietaComponent,FormsModule]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FormularioDietaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
