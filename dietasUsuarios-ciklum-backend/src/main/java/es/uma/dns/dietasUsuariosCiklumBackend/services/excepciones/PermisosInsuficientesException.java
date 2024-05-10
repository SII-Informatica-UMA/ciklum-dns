package es.uma.dns.dietasUsuariosCiklumBackend.services.excepciones;

public class PermisosInsuficientesException extends RuntimeException {
    public PermisosInsuficientesException() {
        super();
    }

    public PermisosInsuficientesException(String msg) {
        super(msg);
    }
}
