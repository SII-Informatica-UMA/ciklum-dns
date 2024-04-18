import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Dieta } from '../entities/dieta';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { ConfirmacionEliminarComponent } from '../dau-confirmacion-eliminar/dau-confirmacion-eliminar.component';
import { AsignarDietaComponent } from '../dau-asignar-dieta/dau-asignar-dieta.component';
import { Cliente } from '../entities/cliente';
import { FormularioDietaComponent } from '../dau-formulario-dieta/dau-formulario-dieta.component';
import { UsuariosService } from '../services/usuarios.service';
import { Rol } from '../entities/login';

@Component({
  selector: 'app-detalle-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dau-detalle-dieta.component.html',
  styleUrl: './dau-detalle-dieta.component.css'
})
export class DetalleDietaComponent {

  @Input() dieta?: Dieta;
  @Output() dietaEliminada = new EventEmitter<number>();
  @Output() dietaEditada = new EventEmitter<Dieta>();
  @Output() clienteAsignado = new EventEmitter<Cliente>();

  constructor(private modalService: NgbModal, private usuariosService: UsuariosService) { }

    //===============================GESTION DE ROL================================================
    private get rol() {
      return this.usuariosService.rolCentro;
    }
  
    isAdministrador(): boolean {
      console.log("Pregunta admin: "+this.rol);
      return this.rol?.rol == Rol.ADMINISTRADOR;
    }

    isEntrenador(): boolean {
      console.log("Pregunta entrenador: "+this.rol);
      return this.rol?.rol == Rol.ENTRENADOR;
    }

    isCliente(): boolean {
      console.log("Pregunta cliente: "+this.rol);
      return this.rol?.rol == Rol.CLIENTE;
    }

    isGerente(): boolean {
      console.log("Pregunta gerente: "+this.rol);
      return this.rol?.rol == Rol.GERENTE;
    }
    //============================================================================================

  eliminarDieta(): void {
    let ref = this.modalService.open(ConfirmacionEliminarComponent);
    ref.componentInstance.accion = "eliminar";
    ref.componentInstance.dieta =  {...this.dieta};
    ref.result.then((dieta: Dieta) => {
      this.dietaEliminada.emit(this.dieta?.id);
    }, (reason) => {});
    
  }

  asignarDieta(): void {
    let ref = this.modalService.open(AsignarDietaComponent);
    ref.componentInstance.accion = "Asignar";
    ref.result.then((cliente: Cliente) => { /*Tomo el valor del cliente que se elige en el componente de asignar, y lo devuelvo */
      this.clienteAsignado.emit(cliente);
    }, (reason) => {});

  }

  editarDieta(): void {
    let ref = this.modalService.open(FormularioDietaComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.dieta = {...this.dieta};
    ref.result.then((dieta: Dieta) => {
      this.dietaEditada.emit(dieta);
    }, (reason) => {});
  }

}
