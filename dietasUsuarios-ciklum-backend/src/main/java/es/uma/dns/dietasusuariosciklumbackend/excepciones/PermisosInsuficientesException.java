package es.uma.dns.dietasusuariosciklumbackend.excepciones;

public class PermisosInsuficientesException extends Exception {
    public PermisosInsuficientesException() {
        super();
    }

    public PermisosInsuficientesException(String msg) {
        super(msg);
    }
}
