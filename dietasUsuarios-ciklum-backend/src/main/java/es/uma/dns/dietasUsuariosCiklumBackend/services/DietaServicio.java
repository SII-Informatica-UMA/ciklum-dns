package es.uma.dns.dietasUsuariosCiklumBackend.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.EntidadExistenteException;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.EntidadNoEncontradaException;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.PermisosInsuficientesException;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.ClienteDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.EntrenadorDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Cliente;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Entrenador;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.DietaRepository;

@Service
@Transactional
public class DietaServicio {
    
    private static DietaRepository dietaRepo;

    public DietaServicio(DietaRepository dietaRepositorio) {
        dietaRepo = dietaRepositorio;
    }

    @Value(value="${local.server.port}")
	private int port;

    @Autowired
    private RestTemplate restTemplate; //para hacer peticiones


    /* ------------------------ METODOS CONSTRUCCION PETICIONES ---------------------- */

    private URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }

/* ------------------------ FIN METODOS CONSTRUCCION PETICIONES ---------------------- */
/* ----------------------------------------------------------------------------------- */


    public static Optional<List<Dieta>> getDietasDeEntrenador(long id) {
        //TODO: devuelve las dietas de un entrenador específico
        return null;
    }

    public static Optional<Dieta> getDietaDeCliente(long id) {
        //TODO: devuelve la dieta de un cliente específico
        return null;
    }

    public static Optional<List<Cliente>> getClientesDeDieta(long id) {
        //TODO: devuelve los clientes de una dieta específica
        return null;
    }

    public static Optional<Entrenador> getEntrenadorDeDieta(long id) {
        //TODO: devuelve el entrenador de una dieta específica
        return null;
    }


//    //GET
//    public List<Dieta> obtenerDietas() {
//        return dietaRepo.findAll();
//    }
//
//
//    //POST
//    public Long aniadirDieta(Dieta d) { //hecha como en apuntes del profe
//        if (!dietaRepo.existsByNombre(d.getNombre())) {
//            d.setId(null);
//            dietaRepo.save(d);
//            return d.getId();
//        } else {
//            throw new EntidadExistenteException();
//        }
//    }


    //GET y POST
    public Optional<Entrenador> getEntrenador(Long entrenadorId) {
        String ruta = "/entrenador/" + entrenadorId;
        var peticion = get("http", "localhost",port, ruta);
        var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<EntrenadorDTO>() {});
        if (respuesta.getStatusCode().value() == 200) { //si existe el entrenador, lo devuelvo
            Entrenador entrenador = Entrenador.fromEntrenadorDTO(respuesta.getBody()); // Lanza una excepción no controlada
            return Optional.of(entrenador);
        } else if (respuesta.getStatusCode().value() == 403){ //no tengo permisos

            return Optional.empty();
        } else { //No existe el entrenador

            return Optional.empty();
        }
    }


    //GET y PUT
    public Optional<Cliente> getCliente(Long clienteId) {
        String ruta = "/cliente/" + clienteId;
        var peticion = get("http", "localhost",port, ruta);
        var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<ClienteDTO>() {});
        if (respuesta.getStatusCode().value() == 200) { //si existe el entrenador, lo devuelvo
            Cliente cliente = Cliente.fromClienteDTO(respuesta.getBody()); // Lanza una excepción no controlada
            return Optional.of(cliente);
        } else if (respuesta.getStatusCode().value() == 403){ //no tengo permisos
            //throw new PermisosInsuficientesException();
            return Optional.empty();
        } else { //No existe el entrenador
            //throw new EntidadNoEncontradaException();
            return Optional.empty();
        }
    }


    //PUT
    public void asignarDietaCliente(Long clienteId, Dieta dieta)
            throws EntidadNoEncontradaException, PermisosInsuficientesException {

        // No hay que hacer la comprobacion de si existe el cliente, ya la hace el controlador

        // No hay que modificar clientes fuera de nuestro microservicio, solo hay que asignarle al
        // cliente la dieta que nos pasan

        Optional<Cliente> cliente = getCliente(clienteId);
        if (cliente.isPresent()) { //siempre debe ser present ya que sino el get cliente lanza una excepcion
            //cliente.setDieta(dieta); // ERROR, no se puede modificar un objeto que se obtiene de un Optional

            /*-----HAGO PETICION HTTP PARA ACTUALIZAR EL CLIENTE CON SU NUEVA DIETA----- */

            String ruta = "/cliente/" + clienteId;
            var peticion = put("http", "localhost",port, ruta, cliente);

            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<ClienteDTO>() {}); //el put de cliente me devuelve el cliente modificado creo, si quiero podria usarlo

            //si quisiera devolver el cliente, deberia pasarlo de dto a cliente

            /*------FIN PETICION HTTP--------------------------------------------------- */

            if (respuesta.getStatusCode().value() == 403){ //no tengo permisos
                throw new PermisosInsuficientesException();
            } else { //No existe el entrenador
                throw new EntidadNoEncontradaException();
            }

        } else {
            throw new EntidadNoEncontradaException();
        }
    }


    //POST
    public Dieta crearDieta(Dieta d) throws EntidadExistenteException {
        if (!dietaRepo.existsByNombre(d.getNombre())) {
            d.setId(null); // ¿?
            Dieta guardada = dietaRepo.save(d);
            return guardada;
        } else {
            throw new EntidadExistenteException();
        }
    }


    //GET{ID} y PUT{ID}
    public Optional<Dieta> getDieta(Long id) {
//        Optional<Dieta> dieta = dietaRepo.findById(id);
//        if (dieta.isPresent()) {
//            return Optional.of(dieta.get());
//        } else {
//            //throw new EntidadNoEncontradaException();
//            return Optional.empty();
//        }
        return dietaRepo.findById(id);
    }


    //PUT{ID}
    public void modificarDieta(Dieta dietaModificada) {
//        if (dietaRepo.existsById(dietaModificada.getId())) {
//            dietaRepo.save(dietaModificada);
//        } else {
//            throw new EntidadNoEncontradaException();
//        }
        dietaRepo.save(dietaModificada);
    }


    //DELETE{ID}
    public void eliminarDieta(Long id) {
//        if (dietaRepo.existsById(id)) {
//            dietaRepo.deleteById(id);
//        } else {
//            throw new EntidadNoEncontradaException();
//        }
        dietaRepo.deleteById(id);
    }


    //DELETE{ID}
    public boolean existsDieta(long id) {
        //return dietaRepo.findById(id).isPresent();
        return dietaRepo.existsById(id);
    }

}
