import { Component, Input, OnInit } from '@angular/core';
import { Cliente } from '../entities/cliente';
import { DietasService } from '../services/dietas.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EntrenadoresService } from '../services/entrenadores.service';
import { ClientesService } from '../services/clientes.service';
import { CommonModule } from '@angular/common';
import { Dieta } from '../entities/dieta';
import { sex } from '../entities/enumSexo';
import { Usuario } from '../entities/usuario';
import { UsuariosService } from '../services/usuarios.service';
import { AsignacionEntrenamientoService } from '../services/asignacionEntrenamiento.service';

@Component({
  selector: 'app-asignar-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dau-asignar-dieta.component.html',
  styleUrl: './dau-asignar-dieta.component.css'
})
export class AsignarDietaComponent {

  accion?: "Asignar"; /*Aparece en minuscula en el html */
  clientes: Cliente [] = [];
  usuarios: Usuario [] = []; /*Para imprimir en el html el nombre */
  clienteElegido!: Cliente; /*Pongo un cliente por defecto para casos de no elegir a nadie */

  private clienteNoElegido: Cliente = {idUsuario: -1, telefono: '',direccion: '', dni: '', fechaNacimiento: '', sexo: sex.OTRO, id: -1 }

  constructor(public modal: NgbActiveModal,private clientesService: ClientesService, private usuariosService: UsuariosService, private asignacionEntrenamientoService: AsignacionEntrenamientoService) { }

  ngOnInit(): void {
      this.clientes = this.clients();
      this.usuarios = this.usuariosService.getUsuarios();
      this.clienteElegido = this.clienteNoElegido;
  }

  private clients(): Cliente[] {
    let listasigEntr = this.asignacionEntrenamientoService.getAsignacionEntrenamiento().filter( x => this.usuariosService.getUsuario().id == x.idEntrenador);
    let listclient: Cliente[] = []
    
    for (let index = 0; index < listasigEntr.length; index++) {
      let cliente = this.clientesService.getClientes().find( x => x.idUsuario == listasigEntr[index].idCliente);
      if(cliente != undefined){
        listclient.push(cliente);
      } 
    }
    return listclient;
  }

  elegirCliente(cliente: Cliente): void {
    this.clienteElegido = cliente;
  }

  asignarDieta(): void {
    this.modal.close(this.clienteElegido); /*En donde se reciba esto, hay que comprobar que la id no sea -1, pues sera que no eligió a nadie y entonces se ignora */
  }

}
