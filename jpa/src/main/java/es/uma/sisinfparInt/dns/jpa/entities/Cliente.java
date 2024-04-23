package es.uma.sisinfparInt.dns.jpa.entities;

// TO-DO: ETIQUETAS y relaciones==HERENCIA, ENTRENADOR Y DIETAS
public class Cliente {
    private Integer idUsuario;
    private String telefono;
    private String direccion;
    private String dni;
    private String fechaNacimiento; //OJOOOOO-> TIPO FECHA MAYBE
    private String sexo; //OJOOOOO-> ENUM SEX
    // TO-DO: relación dieta
    private Integer id;
}
