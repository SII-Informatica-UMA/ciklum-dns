export interface Dieta {
    nombre: string;
    descripcion: string;
    observaciones: string;
    objetivo: string;
    duracionDias: number;
    alimentos: [string];
    recomendaciones: string;
    id: number;
    idEntrenador: number; // Id del entrenador que crea la dieta
}
