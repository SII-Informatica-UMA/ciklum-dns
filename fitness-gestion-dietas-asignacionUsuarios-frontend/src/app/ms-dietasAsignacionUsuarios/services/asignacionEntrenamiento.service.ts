import { Injectable } from '@angular/core';
import { AsignacionEntrenamiento } from '../entities/asignacionEntrenamiento';


@Injectable({
    providedIn: 'root'
})

export class AsignacionEntrenamientoService {
    
    private asignacionEntrenamiento: AsignacionEntrenamiento [] = [
        {idEntrenador:5, idCliente:4, especialidad: "Taekwondo", id:0, planes: []},
        {idEntrenador:5, idCliente:9, especialidad: "Taekwondo", id:1, planes: []},
        {idEntrenador:6, idCliente:9, especialidad: "AquaGym", id:2, planes: []}
    ];

    constructor() { }

    getAsignacionEntrenamiento(): AsignacionEntrenamiento [] {
        return this.asignacionEntrenamiento;
    }

}