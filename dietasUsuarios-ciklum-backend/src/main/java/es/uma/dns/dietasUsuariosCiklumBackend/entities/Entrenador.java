package es.uma.dns.dietasUsuariosCiklumBackend.entities;

import java.util.List;
import java.util.Objects;
import java.sql.Date;
import java.util.Optional;

import es.uma.dns.dietasUsuariosCiklumBackend.services.DietaServicio;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.EntrenadorDTO;
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
    private Long idCentro;

    @OneToMany(mappedBy="entrenador")
    private List<Dieta> dietas;

//-------------------------------------------------------------------------
// METODOS ----------------------------------------------------------------

    public EntrenadorDTO toEntrenadorDTO() {
        return EntrenadorDTO.builder()
            .idUsuario(super.getId())
            .telefono(telefono)
            .direccion(direccion)
            .dni(dni)
            .fechaNacimiento(fechaNacimiento.toString())
            .fechaAlta(fechaAlta.toString())
            .fechaBaja(fechaBaja.toString())
            .especialidad(especialidad)
            .titulacion(titulacion)
            .experiencia(experiencia)
            .observaciones(observaciones)
            .id(idCentro) //SEGURO? !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            .build();
    }

    public static Entrenador fromEntrenadorDTO(EntrenadorDTO entrenadorDTO) {

        Entrenador entrenador = Entrenador.builder()
                .telefono(entrenadorDTO.getTelefono())
                .direccion(entrenadorDTO.getDireccion())
                .dni(entrenadorDTO.getDni())
                .fechaNacimiento(Date.valueOf(entrenadorDTO.getFechaNacimiento()))
                .fechaAlta(Date.valueOf(entrenadorDTO.getFechaAlta()))
                .fechaBaja(Date.valueOf(entrenadorDTO.getFechaBaja()))
                .especialidad(entrenadorDTO.getEspecialidad())
                .titulacion(entrenadorDTO.getTitulacion())
                .experiencia(entrenadorDTO.getExperiencia())
                .observaciones(entrenadorDTO.getObservaciones())
                .idCentro(entrenadorDTO.getId())
                .dietas(obtenerDietasEntrenador(entrenadorDTO.getId()))
                .build();

        entrenador.setId(entrenadorDTO.getIdUsuario());

        return entrenador;
    }

    private static List<Dieta> obtenerDietasEntrenador(long id) {

        Optional<List<Dieta>> dietas = DietaServicio.getDietasDeEntrenador(id);

        if (dietas.isPresent()) {
            return dietas.get();
        } else {
            return null;
        }

    }

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
