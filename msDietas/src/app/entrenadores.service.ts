import { Injectable } from '@angular/core';
import { Entrenador } from './entrenador';

@Injectable({
    providedIn: 'root'
})

export class EntrenadoresService {

    private entrenadores: Entrenador [] = [
        {idUsuario: 5, telefono: '555555555', direccion: 'Calle cinco', dni: '55555555C', fechaNacimiento: '2024-03-26T:17:00:00.330Z', fechaAlta: '2024-03-26T:17:00:00.330Z', fechaBaja: '2024-03-26T:18:00:00.330Z', especialidad: 'crossfit', titulacion: 'ESO', experiencia: '2 años de coach', observaciones: 'Resolutivo', id:0},
        {idUsuario: 6, telefono: '666666666', direccion: 'Calle seis', dni: '66666666D', fechaNacimiento: '2024-03-26T:19:00:00.330Z', fechaAlta: '2024-03-26T:19:00:00.330Z', fechaBaja: '2024-03-26T:20:00:00.330Z', especialidad: 'cardio', titulacion: 'Bachillerato', experiencia: '1 año de entrenador personal', observaciones: 'Simpático', id:0},
    ]

    constructor() { }

    getEntrenadores(): Entrenador [] {
        return this.entrenadores;
    }

    /*TO-DO POSIBLE ADD/EDITAR/ELIMINAR ENTRENADOR */

    eliminarDietaEntrenador(id: number): void {

        let indice= this.entrenadores.findIndex(d => d.id == id);

        while( indice!=-1){
            this.entrenadores[indice].id=0;
            indice= this.entrenadores.findIndex(d => d.id == id);
        }
       
    }
    
}