package es.uma.sisinfparInt.dns.jpa.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// TO-DO: ETIQUETAS y relaciones==HERENCIA, ENTRENADOR Y DIETAS
@Entity
public class Cliente {
    @Id
    private Integer idUsuario;
    private String telefono;
    private String direccion;
    private String dni;
    private String fechaNacimiento; //OJOOOOO-> TIPO FECHA MAYBE
    private String sexo; //OJOOOOO-> ENUM SEX
    // TO-DO: relación dieta
    private Integer id;
    
    @Override
    public String toString() {
        return "Cliente [idUsuario=" + idUsuario + ", telefono=" + telefono + 
        ", direccion=" + direccion + ", dni=" + dni + ", fechaNacimiento=" + fechaNacimiento + 
        ", sexo=" + sexo + ", id=" + id + "]";
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

        return Objects.equals(idUsuario, other.idUsuario) &&  Objects.equals(telefono, other.telefono) &&
        Objects.equals(direccion, other.direccion) &&  Objects.equals(dni, other.dni) && 
        Objects.equals(fechaNacimiento, other.fechaNacimiento) &&  Objects.equals(sexo, other.sexo);
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
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }



    
}
