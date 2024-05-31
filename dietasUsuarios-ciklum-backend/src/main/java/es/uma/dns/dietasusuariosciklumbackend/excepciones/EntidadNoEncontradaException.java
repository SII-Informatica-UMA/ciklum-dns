package es.uma.dns.dietasusuariosciklumbackend.excepciones;

public class EntidadNoEncontradaException extends Exception {
    public EntidadNoEncontradaException () {
        super();
    }    

    public EntidadNoEncontradaException (String msg) {
        super(msg);
    }
}
