package es.uma.dns.dietasusuariosciklumbackend.excepciones;

public class ArgumentoMaloException extends Exception{
    public ArgumentoMaloException() {
        super();
    }

    public ArgumentoMaloException(String msg) {
        super(msg);
    }
}
