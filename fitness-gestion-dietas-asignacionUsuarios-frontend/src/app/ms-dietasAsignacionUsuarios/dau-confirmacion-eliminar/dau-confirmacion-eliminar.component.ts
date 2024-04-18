import { Component } from '@angular/core';
import  {Dieta} from '../entities/dieta';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirmacion-eliminar',
  standalone: true,
  imports: [],
  templateUrl: './dau-confirmacion-eliminar.component.html',
  styleUrl: './dau-confirmacion-eliminar.component.css'
})
export class ConfirmacionEliminarComponent {
  
  accion?: "eliminar";
  dieta: Dieta = {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [''], recomendaciones: '', id: 0, idEntrenador:5};

  constructor(public modal: NgbActiveModal) { }

  eliminarDieta(): void {
    this.modal.close(this.dieta);
  }
  
}
