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

//-------------------------------------------------------------------------

}
