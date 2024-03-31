import { Component, Input, OnInit } from '@angular/core';
import { Cliente } from '../cliente';
import { DietasService } from '../dietas.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EntrenadoresService } from '../entrenadores.service';
import { ClientesService } from '../clientes.service';
import { CommonModule } from '@angular/common';
import { Dieta } from '../dieta';
import { sex } from '../enumSexo';
import { Usuario } from '../usuario';
import { UsuariosService } from '../usuarios.service';

@Component({
  selector: 'app-asignar-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './asignar-dieta.component.html',
  styleUrl: './asignar-dieta.component.css'
})
export class AsignarDietaComponent implements OnInit {
  accion?: "Asignar"; /*Aparece en minuscula en el html */
  clientes: Cliente [] = [];
  usuarios: Usuario [] = []; /*Para imprimir en el html el nombre */
  clienteElegido!: Cliente; /*Pongo un cliente por defecto para casos de no elegir a nadie */

  private clienteNoElegido: Cliente = {idUsuario: -1, telefono: '',direccion: '', dni: '', fechaNacimiento: '', sexo: sex.OTRO, id: -1 }

  constructor(public modal: NgbActiveModal,private clientesService: ClientesService, private usuariosService: UsuariosService) { }

  ngOnInit(): void {
      this.clientes = this.clientesService.getClientes();
      this.usuarios = this.usuariosService.getUsuarios();
      this.clienteElegido = this.clienteNoElegido;
  }

  elegirCliente(cliente: Cliente): void {
    this.clienteElegido = cliente;
  }

  asignarDieta(): void {
    this.modal.close(this.clienteElegido); /*En donde se reciba esto, hay que comprobar que la id no sea -1, pues sera que no eligió a nadie y entonces se ignora */
  }
}
