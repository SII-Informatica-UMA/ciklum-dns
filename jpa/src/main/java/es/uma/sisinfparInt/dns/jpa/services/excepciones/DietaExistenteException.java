package es.uma.sisinfparInt.dns.jpa.services.excepciones;

public class DietaExistenteException extends RuntimeException {
    public DietaExistenteException () {
        super();
    }

    public DietaExistenteException (String msg) {
        super(msg);
    }
}
