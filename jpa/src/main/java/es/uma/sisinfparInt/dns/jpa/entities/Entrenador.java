package es.uma.sisinfparInt.dns.jpa.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// TO-DO: ETIQUETAS y relaciones==HERENCIA Y CLIENTE

@Entity
public class Entrenador {
    @Id
    private Integer idUsuario;
    private String telefono;
    private String direccion;
    private String dni;
    private String fechaNacimiento; //OJOOOOO-> TIPO FECHA MAYBE
    private String fechaAlta; //OJOOOOO-> TIPO FECHA MAYBE
    private String fechaBaja; //OJOOOOO-> TIPO FECHA MAYBE
    private String especialidad;
    private String titulacion;
    private String experiencia;
    private String observaciones;
    //TO-DO:relacion centro
    private Integer id;
    
    @Override
    public String toString() {
        return "Entrenador [idUsuario=" + idUsuario + ", telefono=" + telefono + 
        ", direccion=" + direccion + ", dni=" + dni + ", fechaNacimiento=" + 
        fechaNacimiento + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + 
        ", especialidad=" + especialidad + ", titulacion=" + titulacion + 
        ", experiencia=" + experiencia + ", observaciones=" + observaciones + ", id=" + id + "]";
    }

    @Override
    public int hashCode() {
       return Objects.hash(idUsuario, telefono, direccion, dni, fechaNacimiento,fechaAlta,
       fechaBaja,especialidad,titulacion,experiencia,observaciones);
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

        return Objects.equals(idUsuario, other.idUsuario) &&  Objects.equals(telefono, other.telefono) &&
        Objects.equals(direccion, other.direccion) &&  Objects.equals(dni, other.dni) && 
        Objects.equals(fechaNacimiento, other.fechaNacimiento) &&  Objects.equals(fechaAlta, other.fechaAlta) &&
        Objects.equals(fechaBaja, other.fechaBaja) && Objects.equals(especialidad, other.especialidad)
        && Objects.equals(titulacion, other.titulacion) && Objects.equals(experiencia, other.experiencia)
        && Objects.equals(observaciones, other.observaciones);
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
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public String getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(String fechaBaja) {
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

    
}
