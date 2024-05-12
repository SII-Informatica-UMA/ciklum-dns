package es.uma.dns.dietasUsuariosCiklumBackend.services;

import java.net.URI;
import java.util.ArrayList;
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
	private static int port;

    @Autowired
    private static RestTemplate restTemplate; //para hacer peticiones


    /* ------------------------ METODOS CONSTRUCCION PETICIONES ---------------------- */

    private static URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }

    private static RequestEntity<Void> get(String scheme, String host, int port, String path) {
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
        //DONE: devuelve las dietas de un entrenador específico
        //Optional<Entrenador> entrenador = getEntrenador(id); //No funciona porque no es static getEntrenador
        List<Dieta> dietasEntrenador = new ArrayList<>();
        Entrenador entrenador = null;

        String ruta = "/entrenador/" + id;
        var peticion = get("http", "localhost",port, ruta);
        var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<EntrenadorDTO>() {});
        if (respuesta.getStatusCode().value() == 200) { //si existe el entrenador, lo devuelvo
            entrenador = Entrenador.fromEntrenadorDTO(respuesta.getBody()); // Lanza una excepción no controlada
            dietasEntrenador = entrenador.getDietas();
            return Optional.of(dietasEntrenador);
        } else {
            return Optional.empty();
        } 

    }

    public static Optional<Dieta> getDietaDeCliente(long id) {
        //DONE: devuelve la dieta de un cliente específico
        Cliente cliente = null;

        String ruta = "/cliente/" + id;
        var peticion = get("http", "localhost",port, ruta);
        var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<ClienteDTO>() {});
        if (respuesta.getStatusCode().value() == 200) { //si existe el entrenador, lo devuelvo
            cliente = Cliente.fromClienteDTO(respuesta.getBody()); // Lanza una excepción no controlada
            return Optional.of(cliente.getDieta());
        } else {
            return Optional.empty();
        }

    }

    public static Optional<List<Cliente>> getClientesDeDieta(long id) {
        //DONE: devuelve los clientes de una dieta específica
        return Optional.of(dietaRepo.findById(id).get().getClientes()); //He supuesto que ya se comprueba en el controlador que la dieta existe
    }

    public static Optional<Entrenador> getEntrenadorDeDieta(long id) {
        //DONE: devuelve el entrenador de una dieta específica
        return Optional.of(dietaRepo.findById(id).get().getEntrenador()); //He supuesto que ya se comprueba en el controlador que la dieta existe
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
            return Optional.empty();
        } else { //No existe el entrenador
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
        Cliente c = cliente.get();
        List<Cliente> listClientes = dieta.getClientes();
        if (!listClientes.contains(c)) { //compruebo el cliente no tuviese ya esa dieta,
            listClientes.add(c);         //para no asignarle la dieta dos veces al mismo cliente
            dieta.setClientes(listClientes);
            modificarDieta(dieta); //guardo la dieta, que ya ha sido asignada al cliente nuevo
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
        return dietaRepo.findById(id);
    }


    //PUT{ID}
    public void modificarDieta(Dieta dietaModificada) {
        dietaRepo.save(dietaModificada);
    }


    //DELETE{ID}
    public void eliminarDieta(Long id) {
        dietaRepo.deleteById(id);
    }


    //DELETE{ID}
    public boolean existsDieta(long id) {
        return dietaRepo.existsById(id);
    }


}
