package es.uma.dns.dietasUsuariosCiklumBackend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private String password;
    private boolean administrador;
    private long id;
}
