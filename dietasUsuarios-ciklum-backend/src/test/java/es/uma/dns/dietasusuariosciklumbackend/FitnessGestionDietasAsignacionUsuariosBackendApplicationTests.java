package es.uma.dns.dietasusuariosciklumbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.uma.dns.dietasusuariosciklumbackend.dtos.*;
import es.uma.dns.dietasusuariosciklumbackend.entities.Dieta;
import es.uma.dns.dietasusuariosciklumbackend.repositories.DietaRepository;

import es.uma.dns.dietasusuariosciklumbackend.security.JwtUtil;
import es.uma.dns.dietasusuariosciklumbackend.services.DietaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName(" En el servicio de dietas...")
@DirtiesContext(classMode =  DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FitnessGestionDietasAsignacionUsuariosBackendApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JwtUtil seguridad;

	//------------------------------MOCKITO-----------------------------------------------------------------------------
	@Autowired
	private DietaServicio dietaService;

	private MockRestServiceServer mockServer;

	@Autowired
	private RestTemplate mockitoRestTemplate;

	//------------------------------------------------------------------------------------------------------------------
	@LocalServerPort
	private int port;

	private int portExterno = 57444;

	@Autowired
	private DietaRepository dietaRepo;
	
	@BeforeEach
	public void initializedDatabase(){
		dietaRepo.deleteAll();
	}

	private URI uri(String scheme, String host, int port, String path) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub = ubf.builder()
				.scheme(scheme)
				.host(host).port(port)
				.path(path);
		return ub.build();
	}
	private URI uriQuery(String scheme, String host, int port, String path, boolean esEntrenador, String id) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub;
		if(esEntrenador){
			ub = ubf.builder()
					.scheme(scheme)
					.host(host).port(port)
					.path(path)
					.queryParam("entrenador",id);
		}else{
			ub = ubf.builder()
					.scheme(scheme)
					.host(host).port(port)
					.path(path)
					.queryParam("cliente",id);;
		}

		return ub.build();
	}

	private RequestEntity<Void> get(String scheme, String host, int port, String path, boolean esEntrenador, String id, String token) {
		URI uri = uriQuery(scheme, host,port, path, esEntrenador, id);
		var peticion = RequestEntity.get(uri)
				.accept(APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.build();
		return peticion;
	}

	private RequestEntity<Void> getSinQuery(String scheme, String host, int port, String path,String token) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.get(uri)
				.accept(APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.build();
		return peticion;
	}

	private RequestEntity<Void> delete(String scheme, String host, int port, String path, boolean esEntrenador, String id, String token) {
		URI uri = uriQuery(scheme, host,port, path, esEntrenador, id);
		var peticion = RequestEntity.delete(uri)
				.header("Authorization", "Bearer " +token)
				.build();
		return peticion;
	}
	private RequestEntity<Void> deleteSinQuery(String scheme, String host, int port, String path, String token) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.delete(uri)
				.header("Authorization", "Bearer " +token)
				.build();
		return peticion;
	}

	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object, boolean esEntrenador, String id, String token) {
		URI uri = uriQuery(scheme, host,port, path, esEntrenador, id);
		var peticion = RequestEntity.post(uri)
				.accept(APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> postSinQuery(String scheme, String host, int port, String path, T object,String token) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.post(uri)
				.contentType(APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object, boolean esEntrenador, String id, String token) {
		URI uri = uriQuery(scheme, host,port, path, esEntrenador, id);
		var peticion = RequestEntity.put(uri)
				.contentType(APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> putSinQuery(String scheme, String host, int port, String path, T object, String token) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.put(uri)
				.contentType(APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.body(object);
		return peticion;
	}

	private String entrenador1(){
		return seguridad.generateToken("1");
	}

	private String entrenador2(){
		return seguridad.generateToken("2");
	}

	private String entrenador22(){
		return seguridad.generateToken("22");
	}

	private String entrenadornull(){
		return seguridad.generateToken(null);
	}

	private String cliente4(){
		return seguridad.generateToken("4");
	}

	private String cliente64(){
		return seguridad.generateToken("64");
	}

	private String clientenull(){
		return seguridad.generateToken(null);
	}

	private void compruebaCamposDieta(Dieta expected, Dieta actual) {
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
		assertThat(actual.getDescripcion()).isEqualTo(expected.getDescripcion());
		assertThat(actual.getObservaciones()).isEqualTo(expected.getObservaciones());
		assertThat(actual.getObjetivo()).isEqualTo(expected.getObjetivo());
		assertThat(actual.getDuracionDias()).isEqualTo(expected.getDuracionDias());
		assertThat(actual.getRecomendaciones()).isEqualTo(expected.getRecomendaciones());
		assertThat(actual.getAlimentos()).isEqualTo(expected.getAlimentos());
		assertThat(actual.getEntrenador()).isEqualTo(expected.getEntrenador());
		assertThat(actual.getClientes()).isEqualTo(expected.getClientes());
	}

	private void compruebaCamposPostDieta(DietaNuevaDTO expected, Dieta actual) {
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
		assertThat(actual.getDescripcion()).isEqualTo(expected.getDescripcion());
		assertThat(actual.getObservaciones()).isEqualTo(expected.getObservaciones());
		assertThat(actual.getObjetivo()).isEqualTo(expected.getObjetivo());
		assertThat(actual.getDuracionDias()).isEqualTo(expected.getDuracionDias());
		assertThat(actual.getRecomendaciones()).isEqualTo(expected.getRecomendaciones());
		assertThat(actual.getAlimentos()).isEqualTo(expected.getAlimentos());
	}
	//---------------------MOCKITO----------------------------------------------------------------------------------
	@BeforeEach
	public void init() {
		mockServer = MockRestServiceServer.createServer(mockitoRestTemplate);
	}
	//--------------------------------------------------------------------------------------------------------------


	@BeforeEach
	public void initializeDatabase() {
		dietaRepo.deleteAll();
	}

	//==================================================================================================================

	@Nested
	@DisplayName("cuando la base de datos este vacia de dietas...")
	public class BaseDatosDietasVaciaTest{

		@Nested
		@DisplayName("GET /DIETA")
		public class GetListaDietaTest{

			/*
			 * GET DIETAS DE UN ENTRENADOR  SIN DIETAS
			 * */
			@Test
			@DisplayName("devuelve la lista de dietas de un entrenador vacía")
			public void errordevuelveDietasVaciaEntrenador() throws JsonProcessingException{
				//-------------------------MOCKITO--------------------------------------------------------------------------
				var entrenador = EntrenadorDTO.builder()
						.idUsuario(1L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String entrenadorJson = objectMapper.writeValueAsString(entrenador);

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/entrenador/1")))
								.andExpect(method(HttpMethod.GET))
								.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
								.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(entrenadorJson));
				//------------------------------------------------------------------------------------------------------

				var peticion = get("http", "localhost", port, "/dieta",
						true, Long.toString(1L),entrenador1());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
			}

			/*
			 * GET DIETAS DE UN CLIENTE VACIO
			 * */
			@Test
			@DisplayName("devuelve la lista de dietas de un cliente vacía")
			public void devuelveDietasVaciaCliente() throws JsonProcessingException {
				//---------------------------------MOCKITO------------------------------------------------------------------
				var cliente = ClienteDTO.builder()
						.idUsuario(4L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String clienteJson = objectMapper.writeValueAsString(cliente);

				mockServer.expect(ExpectedCount.twice(),
								requestTo(uri("http", "localhost", portExterno, "/cliente/4")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(clienteJson));
				//----------------------------------------------------------------------------------------------------------

				String token =seguridad.generateToken("4");
				var peticion = get("http", "localhost", port, "/dieta",
						false, Long.toString(4L),token);
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
			}

			/*
			 * ERROR GET DIETAS DE UN ENTRENADOR DISTINTO AL ENTRENADOR CONECTADO
			 * */
			@Test
			@DisplayName("da error cuando el entrenador solicitado es distinto al de la sesion activa")
			public void errorDevuelveDietasEntrenadorDistinto() throws JsonProcessingException{
				//-------------------------MOCKITO----------------------------------------------------------------------
				var entrenador = EntrenadorDTO.builder()
						.idUsuario(1L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String entrenadorJson = objectMapper.writeValueAsString(entrenador);

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/entrenador/1")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(entrenadorJson));

				//------------------------------------------------------------------------------------------------------

				var peticion = get("http", "localhost", port, "/dieta",
						true, Long.toString(1000L),entrenador1());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});


				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

			/*
			 * ERROR GET DIETAS DE UN CLIENTE INEXISTENTE EN SESION DE ENTRENADOR
			 * */
			@Test
			@DisplayName("da error cuando el cliente no existe en la BD y la conexion es de un entrenador")
			public void errorDevuelveDietasClienteInexistente() throws JsonProcessingException{
				var cliente = ClienteDTO.builder()
						.idUsuario(4L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String clienteJson = objectMapper.writeValueAsString(cliente);

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/cliente/1")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.NOT_FOUND));
				var entrenador = EntrenadorDTO.builder()
						.idUsuario(1L)
						.build();

				//------------------------------------------------------------------------------------------------------
				var peticion = get("http", "localhost", port, "/dieta",
						false, Long.toString(4L),entrenador1());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});


				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

			/*
			 * ERROR GET DIETAS CON QUERY INEXISTENTE
			 * */
			@Test
			@DisplayName("da error cuando intentas acceder a una lista de dietas con una url invalida (sin incluir query)")
			public void errorDevuelveDietasURLnoValidaSinQuery() {

				var peticion = getSinQuery("http", "localhost", port, "/dieta",entrenador1());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});

				assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
			}

			/*
			 * ERROR GET DIETAS CON ID ENTRENADOR INVALIDO
			 * */
			@Test
			@DisplayName("da error cuando intentas acceder a una lista de dietas entrenador con una url invalida (el id no es un numero)")
			public void errorDevuelveDietasURLnoValidaIdEntrenadorIncorrecto() throws JsonProcessingException{

				var peticion = get("http", "localhost", port, "/dieta",
						true, "pepito",entrenador1());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

			/*
			 * ERROR GET DIETAS CON ID CLIENTE INVALIDO
			 * */
			@Test
			@DisplayName("da error cuando intentas acceder a una lista de dietas cliente con una url invalida (el id no es un numero)")
			public void errorDevuelveDietasURLnoValidaIdClienteIncorrect() throws JsonProcessingException{


				var peticion = get("http", "localhost", port, "/dieta",
						false, "pepito",cliente64());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

		}

		@Nested
		@DisplayName("POST /DIETA")
		public class PostDietaTest{
			/*
			 *  POST DIETAS CORRECTAMENTE
			 * */
			@Test
			@DisplayName("inserta correctamente una dieta dando ID de Entrenador")
			public void insertaDietaIndicandoEntrenadorConId() throws JsonProcessingException{
				var entrenador = EntrenadorDTO.builder()
						.idUsuario(1L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String entrenadorJson = objectMapper.writeValueAsString(entrenador);

				mockServer.expect(ExpectedCount.twice(),
								requestTo(uri("http", "localhost", portExterno, "/entrenador/1")))
								.andExpect(method(HttpMethod.GET))
								.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
								.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(entrenadorJson));


				List<String> alimentos = new ArrayList<>();
				alimentos.add("tomate");
				var dieta = DietaNuevaDTO.builder()
						.nombre("prueba")
						.descripcion("descripcion")
						.objetivo("objtv")
						.observaciones("observc")
						.duracionDias(1)
						.alimentos(alimentos)
						.recomendaciones("recomendaciones")
						.build();


				var peticion = post("http", "localhost",port, "/dieta", dieta,
						true, Long.toString(1L),entrenador1());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(201);

				assertThat(respuesta.getHeaders().get("Location").get(0))
						.startsWith("http://localhost:"+port+"/dieta");

				List<Dieta> dietasBD = dietaRepo.findAll();
				Dieta dietaEntidad = dietasBD.stream()
						.filter(p->p.getNombre().equals("prueba"))
						.findFirst()
						.get();

				assertThat(dietasBD).hasSize(1);

				assertThat(respuesta.getHeaders().get("Location")).isNotNull();
				assertThat(respuesta.getHeaders().get("Location").get(0))
						.endsWith("/"+dietaEntidad.getId());

				compruebaCamposPostDieta(dieta, dietaEntidad);

				assertThat(dietaEntidad.getEntrenador()).isEqualTo(1L);
			}

			/*
			 *  ERROR POST DIETAS ENTRENADOR NO EXISTE
			 * */
			@Test
			@DisplayName(" da error insertando una dieta por no existir entrenador con ese id")
			public void errorInsertaDietaEntrenadorInexistente() throws JsonProcessingException{

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/entrenador/4")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.NOT_FOUND));

				List<String> alimentos = new ArrayList<>();
				alimentos.add("tomate");
				var dieta = DietaNuevaDTO.builder()
						.nombre("prueba")
						.descripcion("descripcion")
						.objetivo("objtv")
						.observaciones("observc")
						.duracionDias(1)
						.alimentos(alimentos)
						.recomendaciones("recomendaciones")
						.build();


				var peticion = post("http", "localhost",port, "/dieta", dieta,
						true, "4",cliente4());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

			/*
			 *  ERROR POST DIETAS SIN QUERY
			 * */
			@Test
			@DisplayName(" da error insertando una dieta por no señalar el id del entrenador")
			public void errorInsertaDietaNoQuery() {
				List<String> alimentos = new ArrayList<>();
				alimentos.add("tomate");
				var dieta = DietaNuevaDTO.builder()
						.nombre("prueba")
						.descripcion("descripcion")
						.objetivo("objtv")
						.observaciones("observc")
						.duracionDias(1)
						.alimentos(alimentos)
						.recomendaciones("recomendaciones")
						.build();


				var peticion = postSinQuery("http", "localhost",port, "/dieta", dieta,entrenadornull());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

			/*
			 *  ERROR POST DIETAS CON ID ENTRENADOR INVALIDO
			 * */
			@Test
			@DisplayName(" da error insertando una dieta por no señalar señalar como id de entrenador algo que no tiene relacion")
			public void errorInsertaDietaQueryInvalida() {
				List<String> alimentos = new ArrayList<>();
				alimentos.add("tomate");
				var dieta = DietaNuevaDTO.builder()
						.nombre("prueba")
						.descripcion("descripcion")
						.objetivo("objtv")
						.observaciones("observc")
						.duracionDias(1)
						.alimentos(alimentos)
						.recomendaciones("recomendaciones")
						.build();


				var peticion = post("http", "localhost",port, "/dieta", dieta,
						true, "pepito",null);

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

		}

	}

	@Nested
	@DisplayName("cuando la base de datos esté llena...")
	public class BaseDatosDietasLlenaTest{

		@BeforeEach
		public void insertarDatos(){
			
			// Dieta 1 con 2 clientes (4, 5)
			List<Long> clientes=new ArrayList<>();
			clientes.add(4L);
			clientes.add(5L);  
			var dieta1 = new Dieta();
			dieta1.setId(1L);
			dieta1.setNombre("Mediterranea");
			dieta1.setDescripcion("Comer verdura con aceite de oliva y pescado");
			dieta1.setObservaciones("eso");
			dieta1.setObjetivo("Adelgazar");
			dieta1.setDuracionDias(31);
			dieta1.setRecomendaciones("Llevar dieta equilibrada");
			List<String> alimentos = new ArrayList<>();
			alimentos.add("Aceite de oliva");
			alimentos.add("Pescado");
			dieta1.setAlimentos(alimentos);
			dieta1.setEntrenador(1L);
			dieta1.setClientes(clientes);
			dietaRepo.save(dieta1);


			// Dieta 2 con mismo entrenador creado que dieta1 y 0 clientes
			var dieta2 = new Dieta();
			dieta2.setNombre("De colores");
			dieta2.setDescripcion("Cada dia, comes comidas de un color específico");
			dieta2.setObservaciones("El día que toque marrón no puedes comer chocolate");
			dieta2.setObjetivo("Comer variado de forma divertida");
			dieta2.setDuracionDias(100);
			dieta2.setRecomendaciones("No comer porquerías aunque sean del color dicho");
			List<String> alimentos2 = new ArrayList<>();
			alimentos2.add("Todo");
			dieta2.setAlimentos(alimentos2);
			dieta2.setEntrenador(1L);
			dietaRepo.save(dieta2);

			// Dieta 3 con distinto entrenador y 0 clientes
			var dieta3 = new Dieta();
			dieta3.setNombre("Ayuno intermitente");
			dieta3.setDescripcion("Comer dos veces al día en grandes cantidades");
			dieta3.setObservaciones("Si se tiene alguna enfermedad, no es recomendable");
			dieta3.setObjetivo("Adelgazar gracias a estar en déficit calórico");
			dieta3.setDuracionDias(365);
			dieta3.setRecomendaciones("No hacer ayuno de una comida al día");
			List<String> alimentos3 = new ArrayList<>();
			alimentos3.add("Todo");
			dieta3.setAlimentos(alimentos3);
			dieta3.setEntrenador(2L);
			dietaRepo.save(dieta3);
		}

		
		@Nested
		@DisplayName("al consultar una dieta en concreto")
		public class ObtenerDietasTest {
			@Test
			@DisplayName("devuelve la dieta cuando existe")
			public void devuelveDieta() {
				List<Dieta> dietas2 = dietaRepo.findAll();
				Dieta prueba= dietas2.get(0);
				var peticion = getSinQuery("http", "localhost",port, "/dieta/"+prueba.getId(),entrenador1());
				
				var respuesta = restTemplate.exchange(peticion, DietaDTO.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				assertThat(respuesta.hasBody()).isTrue();
				assertThat(respuesta.getBody()).isNotNull();
			}
			
			@Test
			@DisplayName("da error cuando no existe la dieta")
			public void errorCuandoDietaNoExiste() {
				var peticion = getSinQuery("http", "localhost",port, "/dieta/47",entrenador1());
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando no se realiza una bad request")
			public void errorCuandoBadRequest() {
				var peticion = getSinQuery("http", "localhost",port, "/dieta/lll",entrenador1());
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando alguien intenta acceder sin token")
			public void errorCuandoAccesoIndebido() {
				var peticion = getSinQuery("http", "localhost",port, "/dietas/2",null);
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando un cliente intenta acceder a una dieta que no es suya")
			public void errorCuandoAccesoIndebidoDeCliente() {
				var peticion = getSinQuery("http", "localhost",port, "/dietas/2",cliente4());
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando un entrenador intenta acceder a una dieta que no es suya")
			public void errorCuandoAccesoIndebidoDeEntrenador() {
				var peticion = getSinQuery("http", "localhost",port, "/dietas/1",entrenador2());
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			
		}

		        @Nested
            @DisplayName("al actualizar una dieta")
            public class ActualizaDieta {
                @Test
                @DisplayName("actualiza correctamente si la dieta existe")
                @DirtiesContext
                public void actualizaCorrectamente() {
					List<String> alimentos = new ArrayList<>();
					alimentos.add("Aceite de oliva");
					alimentos.add("Pescado");
					List<Long> clientes=new ArrayList<Long>();
					clientes.add(4L);
					clientes.add(5L);  
					List<Dieta> dietas2 = dietaRepo.findAll();
					Dieta prueba= dietas2.get(0);
                    var dieta = Dieta.builder()
							.id(prueba.getId())
                            .nombre("Malagueña")
							.descripcion("Comer verdura con aceite de oliva y pescado")
							.observaciones("eso")
							.objetivo("Adelgazar")
							.duracionDias(31)
							.recomendaciones("Llevar dieta equilibrada")
			
							.alimentos(alimentos)
							.entrenador(1L)
							.clientes(clientes)
                            .build();
					
                    var peticion = putSinQuery("http", "localhost",port, "/dieta/"+prueba.getId(), dieta.toDietaDTO(),entrenador1());

                    var respuesta = restTemplate.exchange(peticion,Dieta.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
                    Dieta dietalmacenada = dietaRepo.findById(prueba.getId()).get();
                    assertThat(dietalmacenada.getNombre()).isEqualTo(dieta.getNombre());
                }
                @Test
                @DisplayName("da error cuando la dieta no existe")
                public void errorCuandoDietaNoExiste() {
                    var dieta = Dieta.builder()
                            .nombre("Inexistente")
                            .build();
                    var peticion = putSinQuery("http", "localhost",port, "/dieta/50", dieta,entrenador1());

                    var respuesta = restTemplate.exchange(peticion,Void.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
                    assertThat(respuesta.hasBody()).isEqualTo(false);
                }

				@Test
                @DisplayName("da error cuando se realiza bad request")
                public void errorCuandoBadRequest() {
                    var dieta = Dieta.builder()
                            .nombre("Bad Request")
                            .build();
                    var peticion = putSinQuery("http", "localhost",port, "/dieta/", dieta,entrenador1());

                    var respuesta = restTemplate.exchange(peticion,Void.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
                    assertThat(respuesta.hasBody()).isEqualTo(false);
                }


				@Test
                @DisplayName("da error cuando alguien intenta actualizar sin token")
                public void errorCuandoActualizacionIndebido() {
                    var dieta = Dieta.builder()
                            .nombre("Sin token")
                            .build();
                    var peticion = putSinQuery("http", "localhost",port, "/dietas/1", dieta,null);

                    var respuesta = restTemplate.exchange(peticion,Void.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
                    assertThat(respuesta.hasBody()).isEqualTo(false);
                }

				@Test
                @DisplayName("da error cuando un entrenador intenta actualizar una dieta que no es suya")
                public void errorCuandoActualizacionDeEntrenadorIndebido() {
                    var dieta = Dieta.builder()
                            .nombre("Malagueña")
                            .build();
                    var peticion = putSinQuery("http", "localhost",port, "/dietas/1", dieta,entrenador2());

                    var respuesta = restTemplate.exchange(peticion,Void.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
                    assertThat(respuesta.hasBody()).isEqualTo(false);
                }

				@Test
                @DisplayName("da error cuando un cliente intenta actualizar")
                public void errorCuandoActualizacionDeClienteIndebido() {
                    var dieta = Dieta.builder()
                            .nombre("Malagueña")
                            .build();
                    var peticion = putSinQuery("http", "localhost",port, "/dietas/1", dieta,cliente4());

                    var respuesta = restTemplate.exchange(peticion,Void.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
                    assertThat(respuesta.hasBody()).isEqualTo(false);
                }


            }

		@Nested
		@DisplayName("al eliminar una dieta")
		public class EliminarDietasTest {
			@Test
			@DisplayName("la elimina cuando existe")
			public void eliminarDietaCorrectamente() {
				List<Dieta> dietas2 = dietaRepo.findAll();
				Dieta prueba= dietas2.get(0);
				var peticion = deleteSinQuery("http", "localhost",port, "/dieta/"+prueba.getId(),entrenador1());
				
				var respuesta = restTemplate.exchange(peticion,Void.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				List<Dieta> dietas = dietaRepo.findAll();
				assertThat(dietas).hasSize(2);
				assertThat(dietas).allMatch(c->c.getId()!=1);
			}
			
			@Test
			@DisplayName("da error cuando la dieta no existe")
			public void errorCuandoDietaNoExiste() {
				var peticion = deleteSinQuery("http", "localhost",port, "/dieta/50",entrenador1());
				
				var respuesta = restTemplate.exchange(peticion,Void.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando se hace una bad request")
			public void errorCuandoBadRequest() {
				var peticion = deleteSinQuery("http", "localhost",port, "/dieta/",entrenador1());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}


			//FALTA EL 403 : ACCESO NO AUTORIZADO

			@Test
			@DisplayName("da error cuando alguien intenta borrar una dieta sin token")
			public void errorCuandoBorradoSinToken() {
				var peticion = deleteSinQuery("http", "localhost",port, "/dietas/1",null);

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando un entrenador intenta borrar una dieta que no es suya")
			public void errorCuandoEntrenadorBorraDeFormaIndebida() {
				var peticion = deleteSinQuery("http", "localhost",port, "/dietas/1",entrenador2());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando un cliente intenta borrar una dieta")
			public void errorCuandoClienteIntentaBorrar() {
				var peticion = deleteSinQuery("http", "localhost",port, "/dietas/1",cliente4());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}
		}

		@Nested
		@DisplayName("GET /DIETA")
		public class GetDietaTest{

			/*
			 * GET DIETAS DE UN ENTRENADOR CON DIETAS (1 O MAS)
			 * */
			@Test
			@DisplayName("devuelve la lista de dietas de un entrenador con 1 o más dietas")
			public void devuelveDietasEntrenador() throws JsonProcessingException{
				var entrenador = EntrenadorDTO.builder()
						.idUsuario(1L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String entrenadorJson = objectMapper.writeValueAsString(entrenador);

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/entrenador/1")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(entrenadorJson));

				var peticion = get("http", "localhost", port, "/dieta",
						true, Long.toString(1L),entrenador1());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				assertThat(respuesta.getBody()).isNotEmpty();
				assertThat(respuesta.getBody().size()).isGreaterThanOrEqualTo(1);
			}

			/*
			 * GET DIETAS DE UN CLIENTE CON DIETA (1)
			 * */
			@Test
			@DisplayName("devuelve la lista de dietas de un cliente con 1 dieta")
			public void devuelveDietaCliente() throws JsonProcessingException{
				var cliente = ClienteDTO.builder()
						.idUsuario(4L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String clienteJson = objectMapper.writeValueAsString(cliente);

				mockServer.expect(ExpectedCount.twice(),
								requestTo(uri("http", "localhost", portExterno, "/cliente/4")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(clienteJson));

				var peticion = get("http", "localhost", port, "/dieta",
						false, Long.toString(4L),cliente4());

				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {
						});

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				assertThat(respuesta.getBody()).isNotEmpty();
				assertThat(respuesta.getBody().size()).isEqualTo(1);
			}
		}


		@Nested
		@DisplayName("PUT /DIETA")
		public class PutDietaTest{

			@Test
			@DisplayName("da correcto cuando se asigna una dieta a un cliente")
			public void asignaCorrectamenteUnaDieta() throws JsonProcessingException{
				var cliente = ClienteDTO.builder()
						.idUsuario(9L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String clienteJson = objectMapper.writeValueAsString(cliente);

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/cliente/9")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(clienteJson));
				var entrenador = EntrenadorDTO.builder()
						.idUsuario(1L)
						.build();

				String entrenadorJson = objectMapper.writeValueAsString(entrenador);

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/entrenador/1")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(entrenadorJson));

				List<Dieta> d = dietaRepo.findAll();

				var dieta = d.get(0).toDietaDTO();

				var peticion = put("http", "localhost", port, "/dieta", dieta,
						false, Long.toString(9L), entrenador1());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			}

			@Test
			@DisplayName("da error si no le envias como query un cliente")
			public void errorClienteInexistente() throws JsonProcessingException{


				List<Dieta> d = dietaRepo.findAll();

				var dieta = d.get(0).toDietaDTO();

				var peticion = putSinQuery("http", "localhost", port, "/dieta", dieta, entrenador1());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}

			@Test
			@DisplayName("da error si intentas asignar una dieta siendo entrenador")
			public void errorAsignarConUsuarioCliente() throws JsonProcessingException{
				var cliente = ClienteDTO.builder()
						.idUsuario(64L)
						.build();

				ObjectMapper objectMapper = new ObjectMapper();
				String clienteJson = objectMapper.writeValueAsString(cliente);

				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/cliente/64")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.OK)
								.contentType(APPLICATION_JSON)
								.body(clienteJson));



				mockServer.expect(ExpectedCount.once(),
								requestTo(uri("http", "localhost", portExterno, "/entrenador/64")))
						.andExpect(method(HttpMethod.GET))
						.andExpect(header("Authorization","Bearer " + DietaServicio.getToken()))
						.andRespond(withStatus(HttpStatus.NOT_FOUND));

				List<Dieta> d = dietaRepo.findAll();

				var dieta = d.get(0).toDietaDTO();

				var peticion = put("http", "localhost", port, "/dieta", dieta,
						false, Long.toString(64L), cliente64());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				mockServer.verify();
				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			}
		}
	}
	


}
