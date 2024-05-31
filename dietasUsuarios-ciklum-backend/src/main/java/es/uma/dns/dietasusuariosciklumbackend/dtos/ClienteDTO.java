package es.uma.dns.dietasusuariosciklumbackend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {
    private long idUsuario;
    private String telefono;
    private String direccion;
    private String dni;
    private String fechaNacimiento;
    private String sexo;
    private long id;
}
