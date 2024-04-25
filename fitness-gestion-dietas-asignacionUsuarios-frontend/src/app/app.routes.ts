import { Routes } from '@angular/router';
import { LoginComponent } from './ms-inicioSesion/login/login.component';
import { ForgottenPasswordComponent } from './ms-inicioSesion/forgotten-password/forgotten-password.component';
import { ResetPasswordComponent } from './ms-inicioSesion/reset-password/reset-password.component';
import { ListadoUsuarioComponent } from './ms-inicioSesion/listado-usuario/listado-usuario.component';
import { PrincipalComponent } from './ms-inicioSesion/principal/principal.component';
import { DauPrincipalComponent } from './ms-dietasAsignacionUsuarios/dau-principal/dau-principal.component';

export const routes: Routes = [
  {
    path: 'dietas',
    component: DauPrincipalComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'forgotten-password',
    component: ForgottenPasswordComponent
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent
  },
  {
    path: 'usuarios',
    component: ListadoUsuarioComponent
  },
  {
    path: '',
    component: PrincipalComponent
  }
];
