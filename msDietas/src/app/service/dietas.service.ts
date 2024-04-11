import { Injectable } from '@angular/core';
import { Dieta } from '../entities/dieta';
import { MensajeError } from '../mensaje-error/mensaje-error';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Injectable({
    providedIn: 'root'
})

export class DietasService {

    private dietas: Dieta [] = [
        {nombre: 'Mediterranea', descripcion: 'Nutritiva y sabrosa', observaciones: 'Abundante aceite', objetivo: 'Bajar grasa', duracionDias: 4, alimentos: ['Aceite,Lechuga,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'] , recomendaciones: 'Beber agua' , id: 1, idEntrenador: 5},
        {nombre: 'Vegetal', descripcion: 'Saludable', observaciones: 'Abundante verdura', objetivo: 'Ganar nutrientes', duracionDias: 2, alimentos: ['Lechuga'] , recomendaciones: 'Hacer deporte' , id: 2, idEntrenador: 5},
        {nombre: 'Asiática', descripcion: 'Variedad de sabores', observaciones: 'Abundante soja y salsas', objetivo: 'Mantener masa muscular', duracionDias: 6, alimentos: ['Soja'] , recomendaciones: 'Hacer cinco comidas al día' , id: 3, idEntrenador: 6},
    ];

    constructor(private modalService: NgbModal) { }

    getDietas(): Dieta [] {
        return this.dietas;
    }

    addDieta(dieta: Dieta) {
        dieta.id = Math.max(...this.dietas.map(d => d.id)) + 1;
        this.dietas.push(dieta);
    }

    editarDieta(dieta: Dieta) {
        
        let indice = this.dietas.findIndex(d => d.id == dieta.id);
        if(indice==-1){
            let ref=this.modalService.open(MensajeError);
            ref.componentInstance.error="ERROR 404: La dieta no existe";
        }
        this.dietas[indice] = dieta;
    }

    eliminarDieta(id: number) {
        let indice = this.dietas.findIndex(d => d.id == id);
        if(indice==-1){
            let ref=this.modalService.open(MensajeError);
            ref.componentInstance.error="ERROR 404: La dieta no existe";
        }
        this.dietas.splice(indice, 1);
    }
    
}
