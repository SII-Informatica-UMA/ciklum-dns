//------------TO DO---------//
//////////////////////////////
//////////////////////////////
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Dieta } from '../dieta';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { ConfirmacionEliminar } from '../confirmacion-eliminar/confirmacion-eliminar';


@Component({
  selector: 'app-detalle-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detalle-dieta.component.html',
  styleUrl: './detalle-dieta.component.css'
})
export class DetalleDietaComponent {
  @Input() dieta?: Dieta;
  @Input() esEntrenador?: Boolean;
  @Output() dietaEliminada = new EventEmitter<number>();
  @Output() dietaEditada = new EventEmitter<Dieta>();

  constructor(private modalService: NgbModal) { }

  eliminarDieta(): void {
    let ref = this.modalService.open(ConfirmacionEliminar);
    ref.componentInstance.accion = "eliminar";
    ref.componentInstance.dieta =  {...this.dieta};
    ref.result.then((dieta: Dieta) => {
      this.dietaEliminada.emit(this.dieta?.id);
    }, (reason) => {});
    
  }
}
