package es.uma.dns.dietasusuariosciklumbackend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorDTO {
    private long idUsuario;
    private String telefono;
    private String direccion;
    private String dni;
    private String fechaNacimiento;
    private String fechaAlta;
    private String fechaBaja;
    private String especialidad;
    private String titulacion;
    private String experiencia;
    private String observaciones;
    private long id;
}
