package es.uma.sisinfparInt.dns.jpa.services.excepciones;

public class PermisosInsuficientesException extends RuntimeException {
    public PermisosInsuficientesException() {
        super();
    }

    public PermisosInsuficientesException(String msg) {
        super(msg);
    }
}
