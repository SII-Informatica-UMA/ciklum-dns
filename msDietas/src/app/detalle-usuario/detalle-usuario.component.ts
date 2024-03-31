//-------TO DO ----------//
import { Component, Input, Output, EventEmitter } from '@angular/core';
import {Usuario } from '../usuario';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ClientesService } from '../clientes.service';
import {EntrenadoresService } from '../entrenadores.service';
import {UsuariosService } from '../usuarios.service';

@Component({
  selector: 'app-detalle-usuario',
  standalone: true,
  imports: [],
  templateUrl: './detalle-usuario.component.html',
  styleUrl: './detalle-usuario.component.css'
})
export class DetalleUsuarioComponent {
  
  @Input() usuario!: Usuario;
  @Input() esEntrenador!: boolean;

  protected rol: String = 'cliente';
  protected mostrarId = false;
  protected botonID = 'Mostrar ID';
  protected ID_texto = '';
  
  constructor(private clientesService: ClientesService, private usuariosService: UsuariosService, private entrenadoresService: EntrenadoresService, private modalService: NgbModal) {
    if(this.esEntrenador){
      this.rol = 'entrenador';
    }
  }

  toggleId() {
    this.mostrarId = !this.mostrarId;
    this.ID_texto = this.mostrarId ? (this.usuario.id).toString() : '';
  }

  

}
