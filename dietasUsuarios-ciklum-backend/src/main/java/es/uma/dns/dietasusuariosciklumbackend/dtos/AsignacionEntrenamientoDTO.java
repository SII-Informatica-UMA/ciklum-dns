package es.uma.dns.dietasusuariosciklumbackend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AsignacionEntrenamientoDTO {
    private long idEntrenador;
    private long idCliente;
    private String especialidad;
    private long id;
    //private List<PlanDTO> planes;
}
