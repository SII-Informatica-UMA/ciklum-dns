import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-mensaje-error',
  standalone: true,
  templateUrl: './mensaje-error.html',
  styleUrl: './mensaje-error.css'
})
export class MensajeError {
  error?: "codigo";

  constructor(public modal: NgbActiveModal) { }

  
}