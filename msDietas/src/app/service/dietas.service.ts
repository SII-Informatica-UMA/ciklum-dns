import { Injectable } from '@angular/core';
import { Dieta } from '../entities/dieta';

@Injectable({
    providedIn: 'root'
})

export class DietasService {

    private dietas: Dieta [] = [
        {nombre: 'Mediterranea', descripcion: 'Nutritiva y sabrosa', observaciones: 'Abundante aceite', objetivo: 'Bajar grasa', duracionDias: 4, alimentos: ['Aceite,Lechuga,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'] , recomendaciones: 'Beber agua' , id: 1},
        {nombre: 'Vegetal', descripcion: 'Saludable', observaciones: 'Abundante verdura', objetivo: 'Ganar nutrientes', duracionDias: 2, alimentos: ['Lechuga'] , recomendaciones: 'Hacer deporte' , id: 2},
        {nombre: 'Asiática', descripcion: 'Variedad de sabores', observaciones: 'Abundante soja y salsas', objetivo: 'Mantener masa muscular', duracionDias: 6, alimentos: ['Soja'] , recomendaciones: 'Hacer cinco comidas al día' , id: 3},
    ];

    constructor() { }

    getDietas(): Dieta [] {
        return this.dietas;
    }

    addDieta(dieta: Dieta) {
        dieta.id = Math.max(...this.dietas.map(d => d.id)) + 1;
        this.dietas.push(dieta);
    }

    editarDieta(dieta: Dieta) {
        let indice = this.dietas.findIndex(d => d.id == dieta.id);
        this.dietas[indice] = dieta;
    }

    eliminarDieta(id: number) {
        let indice = this.dietas.findIndex(d => d.id == id);
        this.dietas.splice(indice, 1);
    }
    
}
