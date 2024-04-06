import { Injectable } from '@angular/core';
import {Usuario } from '../entities/usuario';
import { Rol, RolCentro } from '../entities/login';

@Injectable({
    providedIn: 'root'
})

export class UsuariosService {

    private usuarios: Usuario [] = [
        {nombre: 'user1', apellido1: 'apell11', apellido2: 'apell21', email:'1@m.com',id:1}, //0
        {nombre: 'user2', apellido1: 'apell12', apellido2: 'apell22', email:'2@m.com',id:2}, //1
        {nombre: 'client3', apellido1: 'apell13', apellido2: 'apell23', email:'3@m.com',id:3}, //2
        {nombre: 'client4', apellido1: 'apell14', apellido2: 'apell24', email:'4@m.com',id:4}, //3
        {nombre: 'trainer5', apellido1: 'apell15', apellido2: 'apell25', email:'5@m.com',id:5}, //4
        {nombre: 'Manuel', apellido1: 'Carrasco', apellido2: 'García', email:'manuel@email.com',id:6}, //5
        {nombre: 'admin', apellido1: 'admin', apellido2: 'admin', email:'admin@email.com',id:7}, //6
        {nombre: 'Gerente', apellido1: 'Gerente', apellido2: 'Gerente', email:'gerente@email.com',id:8}, //7
        {nombre: 'client5', apellido1: 'apell19', apellido2: 'apell29', email:'9@m.com',id:9} //8
    ];

    private rolesEjemplo: RolCentro [] = [
        {rol:Rol.ADMINISTRADOR, centro:1, nombreCentro:'centro1'},
        {rol:Rol.GERENTE, centro:2, nombreCentro:'centro2'},
        {rol:Rol.CLIENTE, centro:2, nombreCentro:'centro2'},
        {rol:Rol.ENTRENADOR, centro:2, nombreCentro:'centro2'},
    ];

    private usuario: Usuario | null = null;
    
    private _rolCentro?: RolCentro;

    get rolCentro(): RolCentro | undefined {
        return this._rolCentro;
    }
    
    set rolCentro(r: RolCentro | undefined) {
        this._rolCentro = r;
    }

    constructor() {
        this.usuario = this.getUsuario();
        this._rolCentro = this.getRol();
    }

    getUsuarios(): Usuario [] {
        return this.usuarios;
    }

    /*TO-DO POSIBLE ADD/EDITAR/ELIMINAR USUARIO */

    eliminarUsuario(id: number) {
        let indice = this.usuarios.findIndex(d => d.id == id);
        this.usuarios.splice(indice, 1);
    }

    //TODO: Posible método donde se implementará la obtención del inicio de sesión.
    //      Está sujeto a cambios
    getUsuario(): Usuario{

        if(this.usuario == null){
        // Aquí debería ir el código para obtener el usuario logueado
        // Este usuario es de prueba
            this.usuario = this.usuarios[8];
        }

        return this.usuario;

    }

    // Mock temporal para tomar un rol de ejemplo segun el usuario
    private getRol(): RolCentro {
        let rolAux;
        if(this.getUsuario() == this.usuarios[6]){
            // Admin
            rolAux = this.rolesEjemplo[0];
        }else if(this.getUsuario() == this.usuarios[7]){
            // Gerente
            rolAux = this.rolesEjemplo[1];
        }else if(this.getUsuario() == this.usuarios[4] || this.getUsuario() == this.usuarios[5]){
            // Entrenadores
            rolAux = this.rolesEjemplo[3];
        }else{
            // Clientes
            rolAux = this.rolesEjemplo[2];
        }
        return rolAux;
    }

}
