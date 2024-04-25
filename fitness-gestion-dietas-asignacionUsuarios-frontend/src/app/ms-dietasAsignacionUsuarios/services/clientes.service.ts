import { Injectable } from '@angular/core';
import { Cliente } from '../entities/cliente';
import { sex } from '../entities/enumSexo';
import { MensajeErrorComponent } from '../dau-mensaje-error/dau-mensaje-error.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Injectable({
    providedIn: 'root'
})

export class ClientesService {
    
    private clientes: Cliente [] = [
        {idUsuario: 3, telefono: '333333333',direccion: 'Calle tres', dni: '33333333C', fechaNacimiento: '2024-03-26T:15:00:00.330Z', sexo: sex.HOMBRE, id: 0 }, //por defecto no tienen dieta, por eso id = 0
        {idUsuario: 4, telefono: '444444444',direccion: 'Calle cuatro', dni: '44444444D', fechaNacimiento: '2024-03-26T:16:00:00.330Z', sexo: sex.MUJER, id: 1 }, // Para probar si se ve
        {idUsuario: 9, telefono: '555555555',direccion: 'Calle cinco', dni: '555555555E', fechaNacimiento: '2024-03-26T:16:00:00.330Z', sexo: sex.MUJER, id: 2 }, // Para probar si se ve
    ];

    constructor(private modalService: NgbModal) { }

    getClientes(): Cliente [] {
        return this.clientes;
    }


    /*TO-DO POSIBLE ADD/EDITAR/ELIMINAR CLIENTE */

    asignarDieta(idDieta: number, idCliente: number): void {
        let indice = this.clientes.findIndex(c => c.idUsuario == idCliente);
        if(indice==-1){
            let ref=this.modalService.open(MensajeErrorComponent);
            ref.componentInstance.error="ERROR 404: El cliente no existe o no lo tiene asignado";
        }
        else{
            this.clientes[indice].id = idDieta; /*Asigno la id de la dieta en la posicion correspondiente */
        }
        
    }

    eliminarDietaCliente(id: number): void {

        let indice= this.clientes.findIndex(d => d.id == id);

        while( indice!=-1){
            this.clientes[indice].id=0;
            indice= this.clientes.findIndex(d => d.id == id);
        }
       
    }
}