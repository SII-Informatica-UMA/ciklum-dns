import { Component, OnInit, Input } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Dieta } from './dieta';
import { DietasService } from './dietas.service';
import { ClientesService } from './clientes.service';
import { EntrenadoresService } from './entrenadores.service';
import { NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { FormularioDietaComponent} from './formulario-dieta/formulario-dieta.component'
import { DetalleDietaComponent } from './detalle-dieta/detalle-dieta.component';
import { DetalleUsuarioComponent } from './detalle-usuario/detalle-usuario.component';
//import { AppModule } from './app.module';
import { CommonModule, } from '@angular/common';
import { Usuario } from './usuario';
import { Cliente } from './cliente';
import { UsuariosService } from './usuarios.service';

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
    public esEntrenador: boolean = false;

    constructor(private dietasService: DietasService, private usuariosService: UsuariosService, private modalService: NgbModal,private entrenadorService: EntrenadoresService,private clientesService: ClientesService) {
           
    }

    ngOnInit(): void {

        this.usuario = this.usuariosService.getUsuario();
        this.clientes = this.clientesService.getClientes();
        this.esEntrenador = this.check_esEntrenador();  //Utiliza this.clientes y this.usuario
        
        console.log('Entrenador: ' + this.esEntrenador);
        
        this.dietas = this.check_dietas();        //Utiliza this.clientes, this.usuario y this.esEntrenador

    }

    private check_esEntrenador(): boolean {
        let cliente = this.clientes.find( (c) => this.usuario.id == c.idUsuario);
        return cliente === undefined;
    }

    private check_dietas(): Dieta[] {
        
        let dietas: Dieta[] = [];

        let cliente = this.clientes.find( (c) => this.usuario.id == c.idUsuario);

        if(this.esEntrenador){

            dietas = this.dietasService.getDietas();
            
        }else{

            var dieta = this.dietasService.getDietas().find(dieta => dieta.id == cliente?.id);

            if(dieta === undefined){
                dietas = [];
            }else{
                dietas = [dieta];
            }

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

    asignarDieta(idDieta: number, idCliente: number): void {
        if (idCliente != -1){
        this.clientesService.asignarDieta(idDieta,idCliente); /*Solo cambia el campo del id de la dieta en el cliente correspondiente, no hace falta nada mas */
        this.clientesService.getClientes();
        }
        //Sino, no se eligió a ningún cliente, no hay que hacer nada 
    }
}
