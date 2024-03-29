import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Dieta } from './dieta';
import {DietasService } from './dietas.service';
import {ClientesService } from './clientes.service';
import {EntrenadoresService } from './entrenadores.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioDietaComponent} from './formulario-dieta/formulario-dieta.component'
import { DetalleDietaComponent } from './detalle-dieta/detalle-dieta.component';
import { AppModule } from './app.module';
import { CommonModule, } from '@angular/common';
import { Cliente } from './cliente';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,DetalleDietaComponent,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  dietas: Dieta [] = [];
  clientes: Cliente [] = [];
  dietaElegida?: Dieta;
  clienteElegido?: Cliente;
  title: any;

  constructor(private dietasService: DietasService, private modalService: NgbModal,private entrenadorService: EntrenadoresService,private clientesService: ClientesService) { }

  ngOnInit(): void {
      this.dietas = this.dietasService.getDietas();
      this.clientes = this.clientesService.getClientes();
  }

  elegirDieta(dieta: Dieta): void {
    this.dietaElegida = dieta;
  }

  elegirCliente(cliente: Cliente): void {
    this.clienteElegido = cliente;
  }

  aniadirDieta(): void {
    let ref = this.modalService.open(FormularioDietaComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.dieta = {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [''], recomendaciones: '', id: 0};
    ref.result.then((dieta: Dieta) => {
      this.dietasService.addDieta(dieta);
      this.dietas = this.dietasService.getDietas();
    }, (reason) => {});

  }

  dietaEditada(dieta: Dieta): void {
    this.dietasService.editarDieta(dieta);
    this.dietas = this.dietasService.getDietas();
    this.dietaElegida = this.dietas.find(d => d.id == dieta.id);
  }

  eliminarDieta(id: number): void {
    this.clientesService.eliminarDietaCliente(id);
    this.entrenadorService.eliminarDietaEntrenador(id);
    this.dietasService.eliminarDieta(id);
    this.dietas = this.dietasService.getDietas();
    this.dietaElegida = undefined;
  }

  asignarDieta(idDieta: number, idCliente: number){
    if (idCliente != -1){
      this.clientesService.asignarDieta(idDieta,idCliente); /*Solo cambia el campo del id de la dieta en el cliente correspondiente, no hace falta nada mas */
      this.clientesService.getClientes();
    } else {
      
    } //Sino, no se eligió a ningún cliente, no hay que hacer nada 
  }
}
