package es.uma.dns.dietasUsuariosCiklumBackend.entities;

import java.util.Objects;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.UsuarioDTO;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

// TO-DO: ETIQUETAS y relaciones==HERENCIA
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("U")
public class Usuario {

// ATRIBUTOS --------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private String apellido1;

    private String apellido2;

    private String email;

    private String password;

    private Boolean administrador;

//-------------------------------------------------------------------------
// METODOS ----------------------------------------------------------------

    public UsuarioDTO toUsuarioDTO() {
        return UsuarioDTO.builder()
            .id(id)
            .nombre(nombre)
            .apellido1(apellido1)
            .apellido2(apellido2)
            .email(email)
            .password(password)
            .administrador(administrador)
            .build();
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
        return Objects.hash(id);
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

        return Objects.equals(id, other.id);

    }

//-------------------------------------------------------------------------
    
}
