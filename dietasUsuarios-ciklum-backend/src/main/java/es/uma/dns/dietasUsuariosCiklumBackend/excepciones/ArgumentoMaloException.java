package es.uma.dns.dietasUsuariosCiklumBackend.excepciones;

public class ArgumentoMaloException extends RuntimeException{
    public ArgumentoMaloException() {
        super();
    }

    public ArgumentoMaloException(String msg) {
        super(msg);
    }
}
