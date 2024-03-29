//------------TO DO---------//
//////////////////////////////
//////////////////////////////
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Dieta } from '../dieta';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { ConfirmacionEliminar } from '../confirmacion-eliminar/confirmacion-eliminar';
import { AsignarDietaComponent } from '../asignar-dieta/asignar-dieta.component';
import { Cliente } from '../cliente';


@Component({
  selector: 'app-detalle-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detalle-dieta.component.html',
  styleUrl: './detalle-dieta.component.css'
})
export class DetalleDietaComponent {
  @Input() dieta?: Dieta;
  @Output() dietaEliminada = new EventEmitter<number>();
  @Output() dietaEditada = new EventEmitter<Dieta>();
  @Output() clienteAsignado = new EventEmitter<Cliente>();

  constructor(private modalService: NgbModal) { }

  eliminarDieta(): void {
    let ref = this.modalService.open(ConfirmacionEliminar);
    ref.componentInstance.accion = "eliminar";
    ref.componentInstance.dieta =  {...this.dieta};
    ref.result.then((dieta: Dieta) => {
      this.dietaEliminada.emit(this.dieta?.id);
    }, (reason) => {});
    
  }

  asignarDieta(): void {
    let ref = this.modalService.open(AsignarDietaComponent);
    ref.componentInstance.accion = "Asignar";
    ref.result.then((cliente: Cliente) => { /*Tomo el valor del cliente que se elige en el componente de asignar, y lo devuelvo */
      this.clienteAsignado.emit(cliente);
    }, (reason) => {});

  }
}
