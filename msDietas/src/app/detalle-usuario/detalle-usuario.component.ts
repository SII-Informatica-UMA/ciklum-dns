//-------TO DO ----------//
import { Component, Input, Output, EventEmitter } from '@angular/core';
import {Usuario } from '../entities/usuario';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ClientesService } from '../service/clientes.service';
import {EntrenadoresService } from '../service/entrenadores.service';
import {UsuariosService } from '../service/usuarios.service';
import { Rol } from '../entities/login';

@Component({
  selector: 'app-detalle-usuario',
  standalone: true,
  imports: [],
  templateUrl: './detalle-usuario.component.html',
  styleUrl: './detalle-usuario.component.css'
})
export class DetalleUsuarioComponent {
  
  @Input() usuario!: Usuario;
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
