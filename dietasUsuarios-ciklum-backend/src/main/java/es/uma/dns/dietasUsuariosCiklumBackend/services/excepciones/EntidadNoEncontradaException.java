package es.uma.dns.dietasUsuariosCiklumBackend.services.excepciones;

public class EntidadNoEncontradaException extends RuntimeException {
    public EntidadNoEncontradaException () {
        super();
    }    

    public EntidadNoEncontradaException (String msg) {
        super(msg);
    }
}
