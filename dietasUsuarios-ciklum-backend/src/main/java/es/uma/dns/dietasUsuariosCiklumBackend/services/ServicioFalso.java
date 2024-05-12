package es.uma.dns.dietasUsuariosCiklumBackend.services;

import es.uma.dns.dietasUsuariosCiklumBackend.entities.Cliente;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Entrenador;

import java.util.Optional;

public class ServicioFalso {

    //Estos son los métodos que necesita el controlador para funcionar

    public Optional<Entrenador> getEntrenador(Long entrenadorId) {
        return null;
    }

    public Optional<Cliente> getCliente(Long clienteId) {
        return null;
    }

    public Optional<Dieta> getDieta(long id) {
        return null;
    }

    public boolean existsDieta(long id) {
        return false;
    }

    public void modificarDietaCliente(Long clienteId, Dieta dieta) {
        //null
    }

    public Dieta crearDieta(Dieta dieta) {
        return null;
    }

    public void modificarDieta(Dieta dietaModificada) {
        //null
    }

    public void eliminarDieta(Long id) {
        //null
    }

}
