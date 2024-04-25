package es.uma.sisinfparInt.dns.jpa.entities;

import java.util.Objects;
import java.sql.Date;

import jakarta.persistence.*;

// TO-DO: ETIQUETAS y relaciones==HERENCIA, ENTRENADOR Y DIETAS
@Entity
public class Cliente {

// ATRIBUTOS --------------------------------------------------------------

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer idUsuario;

    private String telefono;

    private String direccion;

    private String dni;

    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Sex sexo;

    @ManyToOne
    private Dieta dieta;

//-------------------------------------------------------------------------
// GETTERS Y SETTERS ------------------------------------------------------


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

    public Sex getSexo() {
        return sexo;
    }

    public void setSexo(Sex sexo) {
        this.sexo = sexo;
    }

    public Dieta getDieta() {
        return this.dieta;
    }

    public void setId(Dieta dieta) {
        this.dieta = dieta;
    }

//-------------------------------------------------------------------------
// OVERRIDES --------------------------------------------------------------

    @Override
    public String toString() {

        return "Cliente [idUsuario="
            + idUsuario
            + ", telefono="
            + telefono
            + ", direccion=" 
            + direccion 
            + ", dni=" 
            + dni 
            + ", fechaNacimiento=" 
            + fechaNacimiento 
            + ", sexo=" 
            + sexo 
            + ", id=" 
            + dieta.getNombre()
            + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, telefono, direccion, dni, fechaNacimiento, sexo);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj){
            return true;
        }
            
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Cliente other = (Cliente) obj;

        return Objects.equals(idUsuario, other.idUsuario)
            && Objects.equals(telefono, other.telefono) 
            && Objects.equals(direccion, other.direccion) 
            && Objects.equals(dni, other.dni) 
            && Objects.equals(fechaNacimiento, other.fechaNacimiento) 
            && Objects.equals(sexo, other.sexo);
    }

//-------------------------------------------------------------------------

}
