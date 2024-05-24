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
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.ClienteDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.EntrenadorDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.DietaRepository;


@Service
@Transactional
public class DietaServicio {
    
    private static DietaRepository dietaRepo;

    @Autowired
    public DietaServicio(DietaRepository dietaRepositorio) {
        dietaRepo = dietaRepositorio;
    }

    @Value(value="${local.server.port}")
	private static int port;

    @Autowired
    private static RestTemplate restTemplate; //para hacer peticiones




//-------------------------------------------------------------------------
// KKBORRAR ---------------------------------------------------------------

// Estas clases solo sirven para hacer más fácil los cambios de borrar las entidades
// cliente, entrenador y usuario


    private class Cliente {
        //KK
    }

    private class Entrenador {
        //KK
    }


//-------------------------------------------------------------------------
//MÉTODOS DE CONSTRUCCIÓN DE PETICIONES -----------------------------------

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

//-------------------------------------------------------------------------
// MÉTODOS DEL SERVICIO ---------------------------------------------------


    //Se usa en la entidad Dieta
    public static Optional<List<Long>> getClientesDeDieta(Long id) {
        //DONE: devuelve los clientes de una dieta específica
        return Optional.of(dietaRepo.findById(id).get().getClientes()); //He supuesto que ya se comprueba en el controlador que la dieta existe
    }


    //Se usa en la entidad Dieta
    public static Optional<Long> getEntrenadorDeDieta(Long id) {
        //DONE: devuelve el entrenador de una dieta específica
        return Optional.of(dietaRepo.findById(id).get().getEntrenador()); //He supuesto que ya se comprueba en el controlador que la dieta existe
    }

    //DONE MODIFICADO NUEVA ESPECIFICACION: devuelve las dietas de un entrenador específico
    //GET (entrenador)
    public static Optional<List<Dieta>> getDietasDeEntrenador(Long id) {
        
        List<Dieta> dietas = dietaRepo.findAll();
        List<Dieta> dietasEntrenador = new ArrayList<>();
        Optional<List<Dieta>> res = Optional.empty();

        for (Dieta d : dietas){
            if (d.getEntrenador() == id){
                dietasEntrenador.add(d);
            }
        }

        if (!dietasEntrenador.isEmpty()){
            res = Optional.of(dietasEntrenador);
        }

        return res;
        
    }


    //MODIFICADO PARA CUMPLIR LA NUEVA ESPECIFICACION
    //GET (cliente)
    public Optional<Dieta> getDietaDeCliente(Long idCliente) {
        //DONE: devuelve la dieta de un cliente específico

        List<Dieta> dietas = dietaRepo.findAll();

        Optional<Dieta> dietaResultado = Optional.empty();

        for (Dieta d : dietas) {
            List<Long> clientes = d.getClientes();

            if (clientes.contains(idCliente)) {
                dietaResultado = Optional.of(d);
                break;
            }

        }

        return dietaResultado;
    }


    //DONE MODIFICADO: Asigna el cliente a la dieta, comprobando para evitar que un cliente tenga dos veces la misma dieta
    //PUT
    public void asignarDietaCliente(Long clienteId, Dieta dieta) {

        // No hay que hacer la comprobacion de si existe el cliente, ya la hace el controlador

        // No hay que modificar clientes fuera de nuestro microservicio, solo hay que asignarle al
        // cliente la dieta que nos pasan
        List<Long> clientes = dieta.getClientes();
        if (!clientes.contains(clienteId)){ //compruebo para no poder asignar la misma dieta dos veces al mismo cliente
            clientes.add(clienteId);
            dieta.setClientes(clientes);
        }

    }

    //DONE MODIFICADO: Publica una nueva dieta
    //POST
    public Dieta crearDieta(Dieta d, Long idEntrenador) throws EntidadExistenteException {

        if (!dietaRepo.existsByNombre(d.getNombre())) {
            d.setEntrenador(idEntrenador); //Aseguro que se asigna el idEntrenador
            d.setId(null); // ¿? --> Supongo es porque la bd se encarga de darle automaticamente un id, vaya a ser que traiga alguno que genere conflicto (el profe lo tenia asi creo que fue de donde lo vi en sus ejemplos)
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
    public boolean existeDieta(long id) {
        return dietaRepo.existsById(id);
    }


    //put
    public boolean existeCliente(Long clienteId) {
        //DONE, recorro todas las dietas de nuestro microservicio y compruebe ese cliente aparece
        boolean res = false;
        List<Dieta> dietas = dietaRepo.findAll();
        for (Dieta d : dietas){
            List<Long> clientes = d.getClientes();
            if (clientes.contains(clienteId)){
                res = true;
                break;
            }
        }
        return res;
    }

    //POST
    public boolean existeEntrenador(Long entrenadorId) {
        //DONE, comprueba si el resultado de buscar una dieta dado un entrenador es vacio o no
        return dietaRepo.findByEntrenador(entrenadorId).isPresent();
    }


}

