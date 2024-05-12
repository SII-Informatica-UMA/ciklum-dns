package es.uma.dns.dietasUsuariosCiklumBackend.excepciones;

public class EntidadNoEncontradaException extends Exception {
    public EntidadNoEncontradaException () {
        super();
    }    

    public EntidadNoEncontradaException (String msg) {
        super(msg);
    }
}
