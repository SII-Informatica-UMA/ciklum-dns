import { Injectable } from '@angular/core';
import {Usuario } from './usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {
  private usuarios: Usuario [] = [
      {nombre: 'user1', apellido1: 'apell11', apellido2: 'apell21', email:'1@m.com',id:1},
      {nombre: 'user2', apellido1: 'apell12', apellido2: 'apell22', email:'2@m.com',id:2},
      {nombre: 'client3', apellido1: 'apell13', apellido2: 'apell23', email:'3@m.com',id:3},
      {nombre: 'client4', apellido1: 'apell14', apellido2: 'apell24', email:'4@m.com',id:4},
      {nombre: 'trainer5', apellido1: 'apell15', apellido2: 'apell25', email:'5@m.com',id:5},
      {nombre: 'trainer6', apellido1: 'apell16', apellido2: 'apell26', email:'6@m.com',id:6},
  ];

  constructor() {}

  getUsuarios(): Usuario [] {
    return this.usuarios;
  }

  /*TO-DO POSIBLE ADD/EDITAR/ELIMINAR USUARIO */

  eliminarUsuario(id: number) {
        let indice = this.usuarios.findIndex(d => d.id == id);
        this.usuarios.splice(indice, 1);
    }

}