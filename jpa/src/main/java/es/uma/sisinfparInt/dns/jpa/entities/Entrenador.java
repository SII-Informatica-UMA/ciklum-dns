package es.uma.sisinfparInt.dns.jpa.entities;

import java.util.List;
import java.util.Objects;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

// TO-DO: ETIQUETAS y relaciones==HERENCIA Y CLIENTE

@Entity
public class Entrenador {

// ATRIBUTOS --------------------------------------------------------------

    @Id
    private Integer idUsuario;

    private String telefono;

    private String direccion;

    private String dni;

    private Date fechaNacimiento; //OJOOOOO-> SQL MEJOR?

    private Date fechaAlta; //OJOOOOO-> SQL MEJOR?

    private Date fechaBaja; //OJOOOOO-> SQL MEJOR?

    private String especialidad;

    private String titulacion;

    private String experiencia;

    private String observaciones;

    //TO-DO:relacion centro y entidad centro
    private Integer id;

    @OneToMany(mappedBy="entrenador")
    private List<Dieta> dietas;

//-------------------------------------------------------------------------
// GETTERS Y SETTERS ------------------------------------------------------

    public List<Dieta> getDietas() {
        return dietas;
    }

    public void setDietas(List<Dieta> dietas) {
        this.dietas = dietas;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//-------------------------------------------------------------------------
// OVERRIDES --------------------------------------------------------------

    @Override
    public String toString() {
        return "Entrenador [idUsuario=" 
            + idUsuario 
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
            + id 
            + ", dietas="
            + dietas
            + "]";
    }

    @Override
    public int hashCode() {
       return Objects.hash(idUsuario, telefono, direccion, dni, fechaNacimiento,fechaAlta, fechaBaja, especialidad, titulacion, experiencia, observaciones, id);
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

        return Objects.equals(idUsuario, other.idUsuario) 
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
            && Objects.equals(id, other.id);
    }

//-------------------------------------------------------------------------
    
}
