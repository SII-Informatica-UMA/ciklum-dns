import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
// import {Usuario } from './usuario';
// import {UsuariosService } from './usuarios.service';

describe('AppComponent', () => {

  // let usuarioMockSevice: UsuariosService;

  beforeEach(async () => {

    // usuarioMockSevice = {
    //   getUsuarios: () => {
    //     return [
    //       {nombre: 'user1', apellido1: 'apell11', apellido2: 'apell21', email:'1@m.com',id:1},
    //       {nombre: 'user2', apellido1: 'apell12', apellido2: 'apell22', email:'2@m.com',id:2},
    //       {nombre: 'client3', apellido1: 'apell13', apellido2: 'apell23', email:'3@m.com',id:3},
    //       {nombre: 'client4', apellido1: 'apell14', apellido2: 'apell24', email:'4@m.com',id:4},
    //       {nombre: 'trainer5', apellido1: 'apell15', apellido2: 'apell25', email:'5@m.com',id:5},
    //       {nombre: 'trainer6', apellido1: 'apell16', apellido2: 'apell26', email:'6@m.com',id:6},
    //     ]
    //   },
    //   eliminarUsuario: (id: number) => { },
    //   getUsuario: () => { return {nombre: 'user1', apellido1: 'apell11', apellido2: 'apell21', email:'1@m.com',id:1} }

    // } as UsuariosService;

    await TestBed.configureTestingModule({
      imports: [AppComponent],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'msDietas' title`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('msDietas');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, msDietas');
  });
});
