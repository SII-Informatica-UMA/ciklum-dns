import { Component } from '@angular/core';
import  {Dieta} from '../dieta';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-formulario-dieta',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './formulario-dieta.component.html',
  styleUrl: './formulario-dieta.component.css'
})
export class FormularioDietaComponent {
  accion?: "Añadir" | "Editar";
  dieta: Dieta = {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [''], recomendaciones: '', id: 0};

  constructor(public modal: NgbActiveModal) { }

  guardarDieta(): void {
    this.modal.close(this.dieta);
  }
  
}
