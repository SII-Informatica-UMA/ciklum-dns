//------------TO DO---------//
//////////////////////////////
//////////////////////////////
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Dieta } from '../dieta';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';

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

  constructor(private modalService: NgbModal) { }

  eliminarDieta(): void {
    this.dietaEliminada.emit(this.dieta?.id);
  }
}
