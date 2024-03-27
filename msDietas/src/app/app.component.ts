import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Dieta } from './dieta';
import {DietasService } from './dietas.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioDietaComponent} from './formulario-dieta/formulario-dieta.component'
import { DetalleDietaComponent } from './detalle-dieta/detalle-dieta.component';
import { AppModule } from './app.module';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,DetalleDietaComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  dietas: Dieta [] = [];
  dietaElegida?: Dieta;

  constructor(private dietasService: DietasService, private modalService: NgbModal) { }

  ngOnInit(): void {
      this.dietas = this.dietasService.getDietas();
  }

  elegirDieta(dieta: Dieta): void {
    this.dietaElegida = dieta;
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
    this.dietasService.eliminarDieta(id);
    this.dietas = this.dietasService.getDietas();
    this.dietaElegida = undefined;
  }
}
