package es.uma.dns.dietasUsuariosCiklumBackend.services.excepciones;

public class EntidadExistenteException extends RuntimeException {
    public EntidadExistenteException () {
        super();
    }

    public EntidadExistenteException (String msg) {
        super(msg);
    }
}
