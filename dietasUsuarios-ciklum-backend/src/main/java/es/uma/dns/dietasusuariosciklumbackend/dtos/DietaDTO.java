package es.uma.dns.dietasusuariosciklumbackend.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DietaDTO {
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String objetivo;
    private int duracionDias;
    private List<String> alimentos;
    private String recomendaciones;
    private long id;
}
