package es.uma.dns.dietasusuariosciklumbackend.excepciones;

public class EntidadExistenteException extends Exception {
    public EntidadExistenteException () {
        super();
    }

    public EntidadExistenteException (String msg) {
        super(msg);
    }
}
