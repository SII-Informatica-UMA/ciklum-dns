package es.uma.sisinfparInt.dns.jpa.services.excepciones;

public class DietaNoEncontradaException extends RuntimeException {
    public DietaNoEncontradaException () {
        super();
    }    

    public DietaNoEncontradaException (String msg) {
        super(msg);
    }
}
