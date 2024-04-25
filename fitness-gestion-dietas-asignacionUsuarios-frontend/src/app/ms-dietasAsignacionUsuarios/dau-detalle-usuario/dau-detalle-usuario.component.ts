import { Component, Input, Output, EventEmitter } from '@angular/core';
import {Usuario } from '../entities/usuario';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ClientesService } from '../services/clientes.service';
import {EntrenadoresService } from '../services/entrenadores.service';
import {UsuariosService } from '../services/usuarios.service';
import { Rol, UsuarioSesion } from '../../ms-inicioSesion/entities/login';

@Component({
  selector: 'app-detalle-usuario',
  standalone: true,
  imports: [],
  templateUrl: './dau-detalle-usuario.component.html',
  styleUrl: './dau-detalle-usuario.component.css'
})
export class DetalleUsuarioComponent {


  @Input() usuario!: UsuarioSesion;
  protected rol: String = 'cliente';
  protected mostrarId = false;
  protected botonID = 'Mostrar ID';
  protected ID_texto = '';
  
  constructor(private clientesService: ClientesService, private usuariosService: UsuariosService, private entrenadoresService: EntrenadoresService, private modalService: NgbModal) {
    
  }

  ngOnInit(): void {
    var rolCentro = this.usuariosService.rolCentro;
    if(rolCentro?.rol == Rol.CLIENTE){
      this.rol = 'cliente';
    }else if(rolCentro?.rol == Rol.ENTRENADOR){
      this.rol = 'entrenador';
    }else if(rolCentro?.rol == Rol.GERENTE){
      this.rol = 'gerente';
    }else{
      this.rol = 'admin';
    }
  }

  toggleId() {
    this.mostrarId = !this.mostrarId;
    this.ID_texto = this.mostrarId ? (this.usuario.id).toString() : '';
  }

}
