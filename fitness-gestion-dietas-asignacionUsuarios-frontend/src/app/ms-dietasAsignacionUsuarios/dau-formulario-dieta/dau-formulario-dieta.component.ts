import { Component } from '@angular/core';
import  {Dieta} from '../entities/dieta';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UsuariosService } from '../services/usuarios.service';

@Component({
  selector: 'app-formulario-dieta',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './dau-formulario-dieta.component.html',
  styleUrl: './dau-formulario-dieta.component.css'
})
export class FormularioDietaComponent {

  accion?: "Añadir" | "Editar";
  dieta: Dieta = {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [''], recomendaciones: '', id: 0, idEntrenador:5};

  constructor(public modal: NgbActiveModal , private usuariosService: UsuariosService) { }

  guardarDieta(): void {
    this.dieta.idEntrenador=this.usuariosService.getUsuario().id;
    this.modal.close(this.dieta);
  }

}
