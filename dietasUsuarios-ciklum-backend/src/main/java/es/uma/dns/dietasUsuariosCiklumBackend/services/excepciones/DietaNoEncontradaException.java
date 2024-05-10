package es.uma.dns.dietasUsuariosCiklumBackend.services.excepciones;

public class DietaNoEncontradaException extends RuntimeException {
    public DietaNoEncontradaException () {
        super();
    }    

    public DietaNoEncontradaException (String msg) {
        super(msg);
    }
}
