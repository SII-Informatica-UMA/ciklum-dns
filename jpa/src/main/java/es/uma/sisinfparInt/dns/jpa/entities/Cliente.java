package es.uma.sisinfparInt.dns.jpa.entities;

import java.util.Objects;
import java.sql.Date;

import jakarta.persistence.*;

// TO-DO: ETIQUETAS y relaciones==HERENCIA, ENTRENADOR Y DIETAS
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DiscriminatorValue("C")
public class Cliente extends Usuario{

// ATRIBUTOS --------------------------------------------------------------

    private String telefono;

    private String direccion;

    private String dni;

    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Sex sexo;

    @ManyToOne
    @JoinColumn(name="dieta_fk")
    private Dieta dieta;

//-------------------------------------------------------------------------
// GETTERS Y SETTERS ------------------------------------------------------

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

        return "Cliente [id=" 
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
            + ", sexo=" 
            + sexo 
            + ", id=" 
            + dieta.getNombre()
            + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(),super.getNombre(),super.getApellido1(),super.getApellido2(),
        super.getEmail(),super.getPassword(), super.getAdministrador(), telefono, direccion, dni,
         fechaNacimiento, sexo);
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

        return Objects.equals(super.getId(), other.getId()) 
            && Objects.equals(super.getNombre(), other.getNombre()) 
            && Objects.equals(super.getApellido1(), other.getApellido1()) 
            && Objects.equals(super.getApellido2(), other.getApellido2()) 
            && Objects.equals(super.getEmail(), other.getEmail()) 
            && Objects.equals(super.getNombre(), other.getNombre()) 
            && Objects.equals(super.getPassword(), other.getPassword())
            && Objects.equals(super.getAdministrador(), other.getAdministrador()) 
            && Objects.equals(telefono, other.telefono) 
            && Objects.equals(direccion, other.direccion) 
            && Objects.equals(dni, other.dni) 
            && Objects.equals(fechaNacimiento, other.fechaNacimiento) 
            && Objects.equals(sexo, other.sexo);
    }

//-------------------------------------------------------------------------

}
