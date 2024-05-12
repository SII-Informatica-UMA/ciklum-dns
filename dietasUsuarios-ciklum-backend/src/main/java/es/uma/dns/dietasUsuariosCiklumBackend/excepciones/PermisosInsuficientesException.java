package es.uma.dns.dietasUsuariosCiklumBackend.excepciones;

public class PermisosInsuficientesException extends Exception {
    public PermisosInsuficientesException() {
        super();
    }

    public PermisosInsuficientesException(String msg) {
        super(msg);
    }
}
