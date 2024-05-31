package es.uma.dns.dietasUsuariosCiklumBackend.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.AsignacionEntrenamientoDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.ArgumentoMaloException;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.PermisosInsuficientesException;
import lombok.Getter;
import org.apache.coyote.BadRequestException;
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
import es.uma.dns.dietasUsuariosCiklumBackend.security.JwtUtil;
import es.uma.dns.dietasUsuariosCiklumBackend.security.SecurityConfguration;
import jakarta.annotation.PostConstruct;


@Service
@Transactional
public class DietaServicio {
    
    private static DietaRepository dietaRepo;

    @Autowired
    private JwtUtil jwtUtil; //Para hacer modificaciones y cosas a los tokens

    private String token; //Usaré un token para todas las peticiones, realmente se debería pactar con los otros ms

    private final String ID_PARA_TOKEN = "150"; //Necesito una id para el token, que pactamos con otros ms en teoria

    @Autowired
    public DietaServicio(DietaRepository dietaRepositorio) {
        dietaRepo = dietaRepositorio;
    }

    @PostConstruct
    private void generaToken() { 
        token = jwtUtil.generateToken(ID_PARA_TOKEN); //no debe hacerse new, para eso está el autowired, y ahora hago postConstruct porque se inyecta tras llamar al constructor y sino daba null
    }

    @Value(value="${local.server.port}")
	private int port;

    @Autowired
    private RestTemplate restTemplate; //para hacer peticiones

    private static int portExterno = 57444;

//-------------------------------------------------------------------------
//MÉTODO DE VER EL ID DE QUIEN SE HA CONECTADO (los {idEntrenador} del OpenAPI) -----------------------------------

    private Long getAuthId() {
        return Long.valueOf(SecurityConfguration.getAuthenticatedUser().get().getUsername());
    }

//-------------------------------------------------------------------------
//MÉTODO DE VER EL TOKEN DEL SERVICIO PARA LOS MOCKS -----------------------------------

    public String getToken() {
        return token;
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


    private record QueryParam(String key, String value) { }

    private static URI uriQuery(String scheme, String host, int port, List<String> paths, List<QueryParam> queryParams) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
                .scheme(scheme)
                .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        for (QueryParam qp : queryParams) {
            ub = ub.queryParam(qp.key(), qp.value());
        }
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization","Bearer " + token)
            .build();
        return peticion;
    }

    private RequestEntity<Void> getQuery(String scheme, String host, int port, List<String> path, List<QueryParam> queryParams) {
        URI uri = uriQuery(scheme, host, port, path, queryParams);
        var peticion = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + token)
                .build();
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
            .header("Authorization","Bearer " + token)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization","Bearer " + token)
            .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization","Bearer " + token)
            .body(object);
        return peticion;
    }

    public URI uriDeDieta(Long id) {
        return uri("http", "localhost", port, "dieta", String.valueOf(id));
    }

//-------------------------------------------------------------------------
// MÉTODOS DEL SERVICIO ---------------------------------------------------


    //Necesita crear un token para hacer las peticiones y tambien recibe el id no del token si no del securityContext

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
    public Optional<List<Dieta>> getDietasDeEntrenador(Long id) throws PermisosInsuficientesException {

        if(!esEntrenador()){
            throw new PermisosInsuficientesException();
        } else {

            Long authId = getAuthId();
            if (!authId.equals(id)) {
                throw new PermisosInsuficientesException();
            } else {

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

        }

    }


    //MODIFICADO PARA CUMPLIR LA NUEVA ESPECIFICACION
    //GET (cliente)
    public Optional<Dieta> getDietaDeCliente(Long idCliente) throws PermisosInsuficientesException {
        //DONE: devuelve la dieta de un cliente específico

        if(!esCliente() && !esEntrenador()){
            throw new PermisosInsuficientesException();
        } else if (esCliente()){

            Long authId = getAuthId();
            if (!authId.equals(idCliente)) {
                throw new PermisosInsuficientesException();
            } else {

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

        } else { //esEntrenador()

            Long authId = getAuthId();

            List<Dieta> dietas = dietaRepo.findAll();

            Optional<Dieta> dietaResultado = Optional.empty();

            for (Dieta d : dietas) {
                List<Long> clientes = d.getClientes();

                if (clientes.contains(idCliente)) {
                    dietaResultado = Optional.of(d);
                    break;
                }

            }

            if (dietaResultado.isPresent()){
                Long entrenadorId = dietaResultado.get().getEntrenador();
                if (!authId.equals(entrenadorId)) {
                    throw new PermisosInsuficientesException();
                }
            }

            return dietaResultado;

        }

    }


    //DONE MODIFICADO: Asigna el cliente a la dieta, comprobando para evitar que un cliente tenga dos veces la misma dieta, y que solo lo puede hacer un entrenador
    //PUT
    public void asignarDietaCliente(Long clienteId, Dieta dieta) throws PermisosInsuficientesException {

        // No hay que hacer la comprobacion de si existe el cliente, ya la hace el controlador

        // No hay que modificar clientes fuera de nuestro microservicio, solo hay que asignarle al
        // cliente la dieta que nos pasan

        if (esEntrenador()){
            List<Long> clientes = dieta.getClientes();
            if (!clientes.contains(clienteId)){ //compruebo para no poder asignar la misma dieta dos veces al mismo cliente
                clientes.add(clienteId);
                dieta.setClientes(clientes);
            }

        } else {
            throw new PermisosInsuficientesException();
        }

    }

    //DONE MODIFICADO: Publica una nueva dieta, solo lo puede hacer el entrenador
    //POST
    public Dieta crearDieta(Dieta d, Long idEntrenador) throws EntidadExistenteException, PermisosInsuficientesException{

        if (esEntrenador()) {

            if (!dietaRepo.existsByNombre(d.getNombre())) {
                d.setEntrenador(idEntrenador); //Aseguro que se asigna el idEntrenador
                d.setId(null); // ¿? --> Supongo es porque la bd se encarga de darle automaticamente un id, vaya a ser que traiga alguno que genere conflicto (el profe lo tenia asi creo que fue de donde lo vi en sus ejemplos)
                Dieta guardada = dietaRepo.save(d);
                return guardada;
            } else {
                throw new EntidadExistenteException();
            }

        } else {
            throw new PermisosInsuficientesException();
        }

    }


    //GET{ID} y PUT{ID}
    public Optional<Dieta> getDieta(Long id) throws PermisosInsuficientesException, ArgumentoMaloException {

            if (esEntrenador()){

                Optional<Dieta> dietaOpt = dietaRepo.findById(id);

                // Si es entrenador, comprueba que el entrenador es el mismo que creo la dieta, osea el que hay en la dieta

                Dieta dieta = dietaOpt.get();

                Long authId = getAuthId();
                Long entrenadorId = dieta.getEntrenador();

                if (!authId.equals(entrenadorId)) {
                    throw new PermisosInsuficientesException();
                }

                return dietaOpt;

            } else if (esCliente()){

                Optional<Dieta> dietaOpt = dietaRepo.findById(id);

                // Si es cliente, comprueba que el cliente tiene el mismo entrenador que la dieta

                Dieta dieta = dietaOpt.get();
                Long entrenadorId = dieta.getEntrenador();

                // Pido los clientes que tiene el entrenador de la dieta
                List<String> rutas = new ArrayList<>();
                rutas.add("/entrena");
                List<QueryParam> queryParams = new ArrayList<>();
                queryParams.add(new QueryParam("entrenador", String.valueOf(entrenadorId)));
                var peticion = getQuery("http", "localhost",portExterno, rutas, queryParams);
                var respuesta = restTemplate.exchange(peticion,
                        new ParameterizedTypeReference<List<AsignacionEntrenamientoDTO>>() {});

                if (respuesta.getStatusCode().value() != 200) {
                    throw new ArgumentoMaloException();
                } else {

                    // Compruebo que el cliente (quien hace la peticion a nuestro microservicio) está en la lista de clientes del entrenador
                    List<AsignacionEntrenamientoDTO> asignaciones = respuesta.getBody();
                    boolean encontrado = false;
                    assert asignaciones != null;
                    for (AsignacionEntrenamientoDTO asignacion : asignaciones) {
                        if (asignacion.getIdCliente() == getAuthId()) { //Por eso compruebo con el getAuthId, me da la id de quien ha pedido esto que se ya que es un cliente
                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        throw new PermisosInsuficientesException();
                    }

                }

                return dietaOpt;

            } else {
                throw new ArgumentoMaloException();
            }

    }


    //PUT{ID}
    //DONE MODIFICADO TENIENDO EN CUENTA QUE SOLO PUEDE MODIFICAR EL ENTRENADOR QUE LA CREÓ
    public void modificarDieta(Dieta dietaModificada) throws PermisosInsuficientesException {
        Long entrenadorCreoDieta = dietaModificada.getEntrenador(); //Toda dieta tiene un entrenador que la crea siempre
        Long idConectado = getAuthId();
        if (esEntrenador() && entrenadorCreoDieta == idConectado){ //Con solo comprobar entrenadorCreoDieta == idConectado valdría, porque ya es un entrenador entonces, pero lo hago por si acaso
            
            dietaRepo.save(dietaModificada);

        } else {
            throw new PermisosInsuficientesException();
        }
    }


    //DELETE{ID}
    //DONE MODIFICADO TENIENDO EN CUENTA QUE SOLO PUEDE BORRARLA EL ENTRENADOR QUE LA CREÓ
    public void eliminarDieta(Long id) throws PermisosInsuficientesException {
        Dieta dieta = dietaRepo.findById(id).get(); //El controlador se asegura que existe
        Long entrenadorCreoDieta = dieta.getEntrenador(); //Toda dieta tiene un entrenador que la crea siempre
        Long idConectado = getAuthId();
        if (esEntrenador() && entrenadorCreoDieta.equals(idConectado)){ //Con solo comprobar entrenadorCreoDieta == idConectado valdría, porque ya es un entrenador entonces, pero lo hago por si acaso
            
            dietaRepo.deleteById(id);

        } else {
            throw new PermisosInsuficientesException();
        }

    }


    //DELETE{ID}
    public boolean existeDieta(Long id) {
        return dietaRepo.existsById(id);
    }


    //DONE
    public boolean existeCliente(Long clienteId) {
        //DONE MODIFICADO, llamo al microservicio de clientes para ver si existe (las peticiones añaden el token que creo en la clase, tuve que hacer un metodo no static)
        boolean res = true;
        
        String ruta = "/cliente/" + clienteId;
        var peticion = get("http", "localhost",portExterno, ruta);
        var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<ClienteDTO>() {});
        if (respuesta.getStatusCode().value() != 200) { //no existe el cliente
            res = false;
        }

        return res;
    }

    //DONE
    public boolean existeEntrenador(Long entrenadorId) {
        //DONE MODIFICADO, llama al microservicio de entrenador para ver si existe (las peticiones añaden el token que creo en la clase, tuve que hacer un metodo no static)
        boolean res = true;

        String ruta = "/entrenador/" + entrenadorId;
        var peticion = get("http", "localhost",portExterno, ruta);
        var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<EntrenadorDTO>() {});
        if (respuesta.getStatusCode().value() != 200) { //no existe el entrenador
            res = false;
        } 
        return res;
    }

    //DONE, usa el getAuthId que es la id de quien hace login, para comprobar si es un cliente llamando a otro microservicio
    public boolean esCliente() {

        Long idCliente = getAuthId();
        return existeCliente(idCliente);
    }


    //DONE, usa el getAuthId que es la id de quien hace login, para comprobar si es un entrenador llamando a otro microservicio
    public boolean esEntrenador() {  
        Long idEntrenador = getAuthId();
        return existeEntrenador(idEntrenador);
    }


}

