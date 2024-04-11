import { plan } from "./plan";

export interface AsignacionEntrenamiento {
    idEntrenador: number;
    idCliente: number;
    especialidad: string;
    id: number;
    planes: plan [];
}