import { Injectable } from '@angular/core';
import {Cliente } from './cliente';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
    private clientes: Cliente [] = [
        {idUsuario: 3, telefono: '111111111',direccion: 'Calle uno', dni: '22222222A', fechaNacimiento: '2024-03-26T:15:00:00.330Z', sexo: sex.HOMBRE, id: 0 }, //por defecto no tienen dieta, por eso id = 0
        {idUsuario: 4, telefono: '333333333',direccion: 'Calle dos', dni: '44444444B', fechaNacimiento: '2024-03-26T:16:00:00.330Z', sexo: sex.MUJER, id: 0 },
    ];

    constructor() { }

    getClientes(): Cliente [] {
        return this.clientes;
    }
    
    

    /*TO-DO POSIBLE ADD/EDITAR/ELIMINAR CLIENTE */
}