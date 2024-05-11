package es.uma.dns.dietasUsuariosCiklumBackend.excepciones;

public class EntidadExistenteException extends Exception {
    public EntidadExistenteException () {
        super();
    }

    public EntidadExistenteException (String msg) {
        super(msg);
    }
}
