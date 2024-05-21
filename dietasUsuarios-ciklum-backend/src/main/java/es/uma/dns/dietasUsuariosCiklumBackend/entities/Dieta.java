package es.uma.dns.dietasUsuariosCiklumBackend.entities;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.services.DietaServicio;
import jakarta.persistence.*;
import lombok.*;

// TO-DO: ETIQUETAS y relaciones
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Dieta {

// ATRIBUTOS --------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private String descripcion;

    private String observaciones;

    private String objetivo;

    private Integer duracionDias;

    private List<String> alimentos;

    private String recomendaciones;

    //@ManyToOne
    //@JoinColumn(name="entrenador_fk")
    private Long entrenador;

    //@OneToMany(mappedBy="dieta")
    private List<Long> clientes;

//-------------------------------------------------------------------------
// METODOS ----------------------------------------------------------------

    public DietaDTO toDietaDTO() {
        return DietaDTO.builder()
            .id(id)
            .nombre(nombre)
            .descripcion(descripcion)
            .observaciones(observaciones)
            .objetivo(objetivo)
            .duracionDias(duracionDias)
            .alimentos(alimentos)
            .recomendaciones(recomendaciones)
            .build();
    }

    public static Dieta fromDietaDTO(DietaDTO dietaDTO) {
        return Dieta.builder()
            .id(dietaDTO.getId())
            .nombre(dietaDTO.getNombre())
            .descripcion(dietaDTO.getDescripcion())
            .observaciones(dietaDTO.getObservaciones())
            .objetivo(dietaDTO.getObjetivo())
            .duracionDias(dietaDTO.getDuracionDias())
            .alimentos(dietaDTO.getAlimentos())
            .recomendaciones(dietaDTO.getRecomendaciones())
            .entrenador(obtenerEntrenadorDieta(dietaDTO.getId()))
            .clientes(obtenerClientesDieta(dietaDTO.getId()))
            .build();
    }

    private static List<Long> obtenerClientesDieta(long id) {

        Optional<List<Long>> clientes = DietaServicio.getClientesDeDieta(id);

        return clientes.orElse(null);

    }

    private static Long obtenerEntrenadorDieta(long id) {

        Optional<Long> entrenador = DietaServicio.getEntrenadorDeDieta(id);

        //Qué pasa si una dieta no tiene entrenador? !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return entrenador.orElse(null);

    }

//-------------------------------------------------------------------------
// OVERRIDES --------------------------------------------------------------

    @Override
    public String toString() {
        return "Dieta [id=" 
            + id + ", nombre="
            + nombre + ", descripcion=" 
            + descripcion 
            + ", observaciones=" 
            + observaciones 
            + ", objetivo=" 
            + objetivo 
            + ", duracionDias=" 
            + duracionDias 
            + ", alimentos=" 
            + alimentos 
            + ", recomendaciones=" 
            + recomendaciones 
            + ", idEntrenador=" 
            + entrenador
            + ", clientes="
            + clientes
            + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj){
            return true;
        }
            
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Dieta other = (Dieta) obj;

        return Objects.equals(id, other.id);

    }

//-------------------------------------------------------------------------

}
