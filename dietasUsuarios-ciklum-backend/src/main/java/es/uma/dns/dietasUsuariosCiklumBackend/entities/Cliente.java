package es.uma.dns.dietasUsuariosCiklumBackend.entities;

import java.util.Objects;
import java.sql.Date;
import java.util.Optional;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.ClienteDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.ArgumentoMaloException;
import es.uma.dns.dietasUsuariosCiklumBackend.services.DietaServicio;
import jakarta.persistence.*;
import lombok.*;

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
// METODOS ----------------------------------------------------------------

    public ClienteDTO toClienteDTO() {
        return ClienteDTO.builder()
            .idUsuario(super.getId())
            .telefono(telefono)
            .direccion(direccion)
            .dni(dni)
            .fechaNacimiento(fechaNacimiento.toString())
            .sexo(sexo.toString())
            .id(dieta.getId()) //SEGURO? !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            .build();
    }

    public static Cliente fromClienteDTO(ClienteDTO clienteDTO) {

        Cliente cliente = Cliente.builder()
                .telefono(clienteDTO.getTelefono())
                .direccion(clienteDTO.getDireccion())
                .dni(clienteDTO.getDni())
                .fechaNacimiento(Date.valueOf(clienteDTO.getFechaNacimiento()))
                .sexo(Sex.valueOf(clienteDTO.getSexo()))
                .dieta(obtenerDietaCliente(clienteDTO.getId()))
                .build();

        cliente.setId(clienteDTO.getIdUsuario());

        return cliente;
    }

    private static Dieta obtenerDietaCliente(long id) {

        Optional<Dieta> dieta = DietaServicio.getDietaDeCliente(id);

        if (dieta.isPresent()) {
            return dieta.get();
        } else {
            return null;
        }

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

//-------------------------------------------------------------------------

}
