package es.uma.sisinfparInt.dns.jpa.entities;

import java.util.Objects;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// TO-DO: ETIQUETAS y relaciones==HERENCIA
@Entity
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("U")
public class Usuario {

// ATRIBUTOS --------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nombre;

    private String apellido1;

    private String apellido2;

    private String email;

    private String password;

    private Boolean administrador;

//-------------------------------------------------------------------------
// GETTERS Y SETTERS ------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

//-------------------------------------------------------------------------
// OVERRIDES --------------------------------------------------------------

    @Override
    public String toString() {
        return "Usuario [id=" 
            + id 
            + ", nombre=" 
            + nombre 
            + ", apellido1=" 
            + apellido1 
            + ", apellido2=" 
            + apellido2 
            + ", email=" 
            + email 
            + ", password=" 
            + password 
            + ", administrador=" 
            + administrador 
            + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido1, apellido2, email,password, administrador);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
            
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Usuario other = (Usuario) obj;

        return Objects.equals(id, other.id) 
            && Objects.equals(nombre, other.nombre) 
            && Objects.equals(apellido1, other.apellido1) 
            && Objects.equals(apellido2, other.apellido2) 
            && Objects.equals(email, other.email) 
            && Objects.equals(password, other.password) 
            && Objects.equals(administrador, other.administrador);
    }

//-------------------------------------------------------------------------
    
}
