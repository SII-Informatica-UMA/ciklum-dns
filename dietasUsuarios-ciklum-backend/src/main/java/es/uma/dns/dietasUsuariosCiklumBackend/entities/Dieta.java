package es.uma.dns.dietasUsuariosCiklumBackend.entities;

import java.util.List;
import java.util.Objects;

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
    private Integer id;

    private String nombre;

    private String descripcion;

    private String observaciones;

    private String objetivo;

    private Integer duracionDias;

    private List<String> alimentos;

    private String recomendaciones;

    @ManyToOne
    @JoinColumn(name="entrenador_fk")
    private Entrenador entrenador;

    @OneToMany(mappedBy="dieta")
    private List<Cliente> clientes;

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
            + entrenador.getId()
            + ", clientes="
            + clientes
            + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, observaciones, objetivo, duracionDias, alimentos, recomendaciones);
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

        return Objects.equals(id, other.id) 
            &&  Objects.equals(nombre, other.nombre) 
            && Objects.equals(descripcion, other.descripcion) 
            &&  Objects.equals(observaciones, other.observaciones) 
            && Objects.equals(objetivo, other.objetivo) 
            &&  Objects.equals(duracionDias, other.duracionDias) 
            && Objects.equals(alimentos, other.alimentos) 
            && Objects.equals(recomendaciones, other.recomendaciones);
    }

//-------------------------------------------------------------------------

}
