package es.uma.dns.dietasUsuariosCiklumBackend.entities;

import java.util.List;
import java.util.Objects;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

// TO-DO: ETIQUETAS y relaciones==HERENCIA Y CLIENTE

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DiscriminatorValue("E")
public class Entrenador extends Usuario{

// ATRIBUTOS --------------------------------------------------------------

    private String telefono;

    private String direccion;

    private String dni;

    private Date fechaNacimiento;

    private Date fechaAlta;

    private Date fechaBaja;

    private String especialidad;

    private String titulacion;

    private String experiencia;

    private String observaciones;

    //TO-DO:relacion centro y entidad centro
    private Integer idCentro;

    @OneToMany(mappedBy="entrenador")
    private List<Dieta> dietas;

//-------------------------------------------------------------------------
// OVERRIDES --------------------------------------------------------------

    @Override
    public String toString() {
        return "Entrenador [id=" 
            + super.getId() 
            + ", nombre=" 
            + super.getNombre()
            + ", apellido1=" 
            + super.getApellido1() 
            + ", apellido2=" 
            + super.getApellido2() 
            + ", email=" 
            + super.getEmail() 
            + ", password=" 
            + super.getPassword() 
            + ", administrador=" 
            + super.getAdministrador() 
            + ", telefono=" 
            + telefono 
            + ", direccion=" 
            + direccion 
            + ", dni=" 
            + dni 
            + ", fechaNacimiento=" 
            + fechaNacimiento 
            + ", fechaAlta=" 
            + fechaAlta 
            + ", fechaBaja=" 
            + fechaBaja 
            + ", especialidad=" 
            + especialidad 
            + ", titulacion=" 
            + titulacion 
            + ", experiencia=" 
            + experiencia 
            + ", observaciones=" 
            + observaciones 
            + ", id=" 
            + idCentro 
            + ", dietas="
            + dietas
            + "]";
    }

    @Override
    public int hashCode() {
       return Objects.hash(super.getId(),super.getNombre(),super.getApellido1(),super.getApellido2(),
       super.getEmail(),super.getPassword(), super.getAdministrador(),telefono, direccion, dni, 
       fechaNacimiento,fechaAlta, fechaBaja, especialidad, titulacion, experiencia, observaciones, idCentro);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
            
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Entrenador other = (Entrenador) obj;

        return Objects.equals(super.getId(), other.getId())
            && Objects.equals(super.getApellido1(), other.getApellido1()) 
            && Objects.equals(super.getApellido2(), other.getApellido2()) 
            && Objects.equals(super.getEmail(), other.getEmail()) 
            && Objects.equals(super.getNombre(), other.getNombre()) 
            && Objects.equals(super.getPassword(), other.getPassword())
            && Objects.equals(super.getAdministrador(), other.getAdministrador())  
            &&  Objects.equals(telefono, other.telefono) 
            && Objects.equals(direccion, other.direccion) 
            &&  Objects.equals(dni, other.dni) 
            && Objects.equals(fechaNacimiento, other.fechaNacimiento) 
            &&  Objects.equals(fechaAlta, other.fechaAlta) 
            && Objects.equals(fechaBaja, other.fechaBaja) 
            && Objects.equals(especialidad, other.especialidad)
            && Objects.equals(titulacion, other.titulacion) 
            && Objects.equals(experiencia, other.experiencia)
            && Objects.equals(observaciones, other.observaciones)
            && Objects.equals(idCentro, other.idCentro);
    }

//-------------------------------------------------------------------------

}
