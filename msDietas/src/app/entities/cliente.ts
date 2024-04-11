import { sex } from './enumSexo';

export interface Cliente {
    idUsuario: number;
    telefono: string;
    direccion: string;
    dni: string;
    fechaNacimiento: string;
    sexo: sex;
    id: number;
}
