import { Component, OnInit, Input } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Dieta } from './dieta';
import { Usuario } from './usuario';
import {DietasService } from './dietas.service';
import {ClientesService } from './clientes.service';
import {EntrenadoresService } from './entrenadores.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioDietaComponent} from './formulario-dieta/formulario-dieta.component'
import { DetalleDietaComponent } from './detalle-dieta/detalle-dieta.component';
import { AppModule } from './app.module';
import { CommonModule, } from '@angular/common';
import { Cliente } from './cliente';
import { UsuariosService } from './usuarios.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,DetalleDietaComponent,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  // @Input() usuario?: Usuario; --> *PARA PROBARLO NO PUEDO USARLO, ASI QUE TOMARE VALORES DETERMINADOS DE MIENTRAS*
  usuario: Usuario;
  dietas: Dieta [] = [];
  clientes: Cliente [] = [];
  dietaElegida?: Dieta;
  clienteElegido?: Cliente;
  esEntrenador?: Boolean;
  title: any;

  // OJOOOOO ----> EL usuariosService SOLO SE UTILIZA EN MI PARTE DEL TRABAJO PARA PROBARLO; EN LA REALIDAD NO SERÍA NECESARIO
  constructor(private dietasService: DietasService, private modalService: NgbModal,private entrenadorService: EntrenadoresService,private clientesService: ClientesService, private usuariosService: UsuariosService) { 
    this.usuario = this.usuariosService.getUsuarios()[4];
  }

  ngOnInit(): void {
      this.clientes = this.clientesService.getClientes();
    
      // Método para obtener la lista de dietas según el usuario.
      let aux = this.clientesService.getClientes();
      let cliente = aux.find( (c) => this.usuario.id == c.idUsuario);
      this.esEntrenador = cliente === undefined;
      if(this.esEntrenador){
        this.dietas = this.dietasService.getDietas();
      }else{
        var dieta = this.dietasService.getDietas().find(dieta => dieta.id == cliente?.id);
        if(dieta === undefined){
          this.dietas = [];
        }else{
          this.dietas = [dieta];
        }
      }
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
