import { Component, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { DetalleUsuarioComponent } from './detalle-usuario/detalle-usuario.component';

@NgModule({
  
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    CommonModule,
    DetalleUsuarioComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
