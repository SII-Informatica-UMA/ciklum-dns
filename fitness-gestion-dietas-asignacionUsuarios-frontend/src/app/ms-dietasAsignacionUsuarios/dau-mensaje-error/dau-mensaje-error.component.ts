import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-mensaje-error',
  standalone: true,
  imports: [],
  templateUrl: './dau-mensaje-error.component.html',
  styleUrl: './dau-mensaje-error.component.css'
})
export class MensajeErrorComponent {
  
  error?: "codigo";

  constructor(public modal: NgbActiveModal) { }

}
