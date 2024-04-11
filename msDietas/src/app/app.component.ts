import { Component, OnInit, Input } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Dieta } from './entities/dieta';
import { DietasService } from './service/dietas.service';
import { ClientesService } from './service/clientes.service';
import { EntrenadoresService } from './service/entrenadores.service';
import { NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { FormularioDietaComponent} from './formulario-dieta/formulario-dieta.component'
import { DetalleDietaComponent } from './detalle-dieta/detalle-dieta.component';
import { DetalleUsuarioComponent } from './detalle-usuario/detalle-usuario.component';
//import { AppModule } from './app.module';
import { CommonModule, } from '@angular/common';
import { Usuario } from './entities/usuario';
import { Cliente } from './entities/cliente';
import { UsuariosService } from './service/usuarios.service';
import { Rol } from './entities/login';
import { AsignacionEntrenamientoService } from './service/asignacionEntrenamiento.service';
import { ConfirmacionEliminar } from './confirmacion-eliminar/confirmacion-eliminar';
import { MensajeError } from './mensaje-error/mensaje-error';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet,DetalleDietaComponent,CommonModule, DetalleUsuarioComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
    
    public usuario!: Usuario;
    public dietas: Dieta [] = [];
    public clientes: Cliente [] = [];
    public dietaElegida?: Dieta;
    public clienteElegido?: Cliente;
    public title: any; //esto sirve de algo?

    constructor(private dietasService: DietasService, private usuariosService: UsuariosService, private modalService: NgbModal,private entrenadorService: EntrenadoresService,private clientesService: ClientesService, private asignacionEntrenamientoService: AsignacionEntrenamientoService) {
           
    }

    ngOnInit(): void {

        this.usuario = this.usuariosService.getUsuario();
        this.clientes = this.clientesService.getClientes();
        this.dietas = this.check_dietas();        //Utiliza this.clientes, this.usuario y this.roles

    }

    //===============================GESTION DE ROL================================================
    private get rol() {
      return this.usuariosService.rolCentro;
    }
  
    isAdministrador(): boolean {
      console.log("Pregunta admin: "+this.rol);
      return this.rol?.rol == Rol.ADMINISTRADOR;
    }

    isEntrenador(): boolean {
      console.log("Pregunta entrenador: "+this.rol);
      return this.rol?.rol == Rol.ENTRENADOR;
    }

    isCliente(): boolean {
      console.log("Pregunta cliente: "+this.rol);
      return this.rol?.rol == Rol.CLIENTE;
    }

    isGerente(): boolean {
      console.log("Pregunta gerente: "+this.rol);
      return this.rol?.rol == Rol.GERENTE;
    }
    //============================================================================================

    private check_dietas(): Dieta[] {
        
        let dietas: Dieta[] = [];

        if(this.isEntrenador()){
          
          dietas = this.dietasService.getDietas().filter( x => this.usuario.id == x.idEntrenador);
            
        }else if(this.isCliente()){
          let cliente = this.clientes.find( (c) => this.usuario.id == c.idUsuario);
          var dieta = this.dietasService.getDietas().find(dieta => dieta.id == cliente?.id);

          if(dieta === undefined){
              dietas = [];
          }else{
              dietas = [dieta];
          }

        }else{
          dietas = this.dietasService.getDietas(); // Por asignarle una dieta, pero esto no importa
        }

        return dietas;

    }

    elegirDieta(dieta: Dieta): void {
        this.dietaElegida = dieta;
    }

    elegirCliente(cliente: Cliente): void {
        this.clienteElegido = cliente;
    }

    aniadirDieta(): void {
        if(!this.isEntrenador()){
          let ref=this.modalService.open(MensajeError);
          ref.componentInstance.error="ERROR 403: No posee los permisos";
        }
        else{
          let ref = this.modalService.open(FormularioDietaComponent);
          ref.componentInstance.accion = "Añadir";
          ref.componentInstance.dieta = {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [''], recomendaciones: '', id: 0};
          ref.result.then((dieta: Dieta) => {
          this.dietasService.addDieta(dieta);
          this.dietas = this.check_dietas();
          }, (reason) => {});
        }
        
    }

    dietaEditada(dieta: Dieta): void {
      if(!this.isEntrenador()){
        let ref=this.modalService.open(MensajeError);
        ref.componentInstance.error="ERROR 403: No posee los permisos";
      }
      else{
        this.dietasService.editarDieta(dieta);
        this.dietas = this.check_dietas();
        this.dietaElegida = this.dietas.find(d => d.id == dieta.id);
      }
    }

    eliminarDieta(id: number): void {
      if(!this.isEntrenador()){
        let ref=this.modalService.open(MensajeError);
        ref.componentInstance.error="ERROR 403: No posee los permisos";
      }
      else{
        this.clientesService.eliminarDietaCliente(id);
        this.entrenadorService.eliminarDietaEntrenador(id);
        this.dietasService.eliminarDieta(id);
        this.dietas = this.check_dietas();
        this.dietaElegida = undefined;
      }
    }

    asignarDieta(idDieta: number, idCliente: number): void {
      if(!this.isEntrenador()){
        let ref=this.modalService.open(MensajeError);
        ref.componentInstance.error="ERROR 403: No posee los permisos";
      }
        if (idCliente != -1){
        this.clientesService.asignarDieta(idDieta,idCliente); /*Solo cambia el campo del id de la dieta en el cliente correspondiente, no hace falta nada mas */
        this.clientesService.getClientes();
        }
        //Sino, no se eligió a ningún cliente, no hay que hacer nada 
    }
}
