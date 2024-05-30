package es.uma.dns.dietasUsuariosCiklumBackend;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaNuevaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.EntrenadorDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.UsuarioDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.DietaRepository;

import es.uma.dns.dietasUsuariosCiklumBackend.security.JwtUtil;
import es.uma.dns.dietasUsuariosCiklumBackend.services.DietaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

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
	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private DietaRepository dietaRepo;


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
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.build();
		return peticion;
	}

	private RequestEntity<Void> getSinQuery(String scheme, String host, int port, String path,String token) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_JSON)
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
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> postSinQuery(String scheme, String host, int port, String path, T object,String token) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object, boolean esEntrenador, String id, String token) {
		URI uri = uriQuery(scheme, host,port, path, esEntrenador, id);
		var peticion = RequestEntity.put(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " +token)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> putSinQuery(String scheme, String host, int port, String path, T object, String token) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.put(uri)
				.contentType(MediaType.APPLICATION_JSON)
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

	//==================================================================================================================
	/*
	 * A COMPLETAR CON AQUELLAS FUNCIONES QUE HAGAN FALTA COMPROBAR
	 * CUANDO NO EXISTE NADA EN LA BASE DE DATOS
	 * */
	@Nested
	@DisplayName("cuando la base de datos esté vacía de dietas...")
	public class BaseDatosDietasVacia{

		//---------------------MOCKITO----------------------------------------------------------------------------------
		@BeforeEach
		public void init() {
			mockServer = MockRestServiceServer.createServer(mockitoRestTemplate);
		}
		//--------------------------------------------------------------------------------------------------------------

		// ========================================GET /DIETAS==========================================================

		/*
		 * GET DIETAS DE UN ENTRENADOR VACIO
		 * OJO -> REVISAR QUE ENTRENADOR CON ID 1 EXISTE
		 * */
		@Test
		@DisplayName("devuelve la lista de dietas de un entrenador vacía")
		public void devuelveDietasVaciaEntrenador() {

			var peticion = get("http", "localhost", port, "/dieta",
					true, Long.toString(1L),entrenador1());


			//---------------------------------MOCKITO------------------------------------------------------------------
			var entrenador = EntrenadorDTO.builder()
					.idUsuario(1L)
					.dni("111111111A")
					.build();


			mockServer.expect(ExpectedCount.once(),
					requestTo(uri("http", "localhost", 8080, "/entrenador/1")))
					.andExpect(method(HttpMethod.GET))
					.andRespond(withStatus(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(entrenador.toString()));


			//----------------------------------------------------------------------------------------------------------
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody()).isEmpty();
		}

		/*
		 * GET DIETAS DE UN CLIENTE VACIO
		 * OJO -> REVISAR QUE CLIENTE CON ID = 2 EXISTE
		 * */
		@Test
		@DisplayName("devuelve la lista de dietas de un cliente vacía")
		public void devuelveDietasVaciaCliente() {
			String token =seguridad.generateToken("4");
			var peticion = get("http", "localhost", port, "/dieta",
					false, Long.toString(4L),token);

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody()).isEmpty();
		}

		/*
		 * ERROR GET DIETAS DE UN ENTRENADOR INEXISTENTE
		 * OJO -> REVISAR QUE ENTRENADOR CON ID = X NO EXISTE
		 * */
		@Test
		@DisplayName("da error cuando el cliente no existe en la base de datos")
		public void errorDevuelveDietasEntrenadorInexistente() {

			var peticion = get("http", "localhost", port, "/dieta",
					true, Long.toString(1000L),entrenador2());

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		/*
		 * ERROR GET DIETAS DE UN CLIENTE INEXISTENTE
		 * OJO -> REVISAR QUE CLIENTE CON ID = X NO EXISTE
		 * */
		@Test
		@DisplayName("da error cuando el cliente no existe en la base de datos")
		public void errorDevuelveDietasClienteInexistente() {

			var peticion = get("http", "localhost", port, "/dieta",
					false, Long.toString(100000L),cliente64());

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		/*
		 * ERROR GET DIETAS CON QUERY INEXISTENTE
		 * */
		@Test
		@DisplayName("da error cuando intentas acceder a una lista de dietas con una url invalida (sin incluir query)")
		public void errorDevuelveDietasURLnoValidaSinQuery() {

			var peticion = getSinQuery("http", "localhost", port, "/dieta",entrenador1());

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
		}

		/*
		 * ERROR GET DIETAS CON ID ENTRENADOR INVALIDO
		 * */
		@Test
		@DisplayName("da error cuando intentas acceder a una lista de dietas entrenador con una url invalida (el id no es un numero)")
		public void errorDevuelveDietasURLnoValidaIdEntrenadorIncorrecto() {

			var peticion = get("http", "localhost", port, "/dieta",
					true, "pepito",entrenador22());

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
		}

		/*
		 * ERROR GET DIETAS CON ID CLIENTE INVALIDO
		 * */
		@Test
		@DisplayName("da error cuando intentas acceder a una lista de dietas cliente con una url invalida (el id no es un numero)")
		public void errorDevuelveDietasURLnoValidaIdClienteIncorrect() {

			var peticion = get("http", "localhost", port, "/dieta",
					false, "pepito",cliente64());

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
		}

		//==============================================================================================================

		// ========================================POST /DIETAS=========================================================
		/*
		*  POST DIETAS CORRECTAMENTE
		* */
		@Test
		@DisplayName("inserta correctamente una dieta dando ID de Entrenador")
		public void insertaDietaIndicandoEntrenadorConId() {
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


			var peticion = post("http", "localhost",port, "/dieta", dieta, true, "1",entrenador1());

			var respuesta = restTemplate.exchange(peticion,Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);

			assertThat(respuesta.getHeaders().get("Location").get(0))
					.startsWith("http://localhost:"+port+"/dietas");

			List<Dieta> dietasBD = dietaRepo.findAll();
			Dieta dietaEntidad = dietasBD.stream()
					.filter(p->p.getNombre().equals("prueba"))
					.findFirst()
					.get();

			assertThat(dietasBD).hasSize(1);

			assertThat(respuesta.getHeaders().get("Location").get(0))
					.endsWith("/"+dietaEntidad.getId());

			compruebaCamposPostDieta(dieta, dietaEntidad);

			assertThat(dietaEntidad.getEntrenador()).isEqualTo(1L);
		}

		/*
		 *  ERROR POST DIETAS ENTRENADOR NO EXISTE
		 *  OJOOOOO -> REVISAR QUE EL ID ENTRENADOR NO EXISTE
		 * */
		@Test
		@DisplayName(" da error insertando una dieta por no existir entrenador con ese id")
		public void errorInsertaDietaEntrenadorInexistente() {
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


			var peticion = post("http", "localhost",port, "/dieta", dieta, true, "100",entrenador22());

			var respuesta = restTemplate.exchange(peticion,Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
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

			assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
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

			assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
		}

		//==============================================================================================================

	}

	/*
	 * A COMPLETAR CON AQUELLAS FUNCIONES QUE HAGAN FALTA PARA
	 * COMPROBAR ELEMENTOS DE LA BASE DE DATOS
	 * */
	@Nested
	@DisplayName("cuando la base de datos esté llena...")
	public class BaseDatosDietasLlena{

		//---------------------MOCKITO----------------------------------------------------------------------------------
		@BeforeEach
		public void init() {
			mockServer = MockRestServiceServer.createServer(mockitoRestTemplate);
		}
		//--------------------------------------------------------------------------------------------------------------

		@BeforeEach
		public void insertarDatos(){
			
			// Dieta 1
			List<Long> clientes=new ArrayList<Long>();
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

			// VER COMO COGER UN CLIENTE

			// Dieta 2 con mismo entrenador creado que dieta1
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
			// VER COMO COGER UN ENTRENADOR-> id=1L
			dieta2.setEntrenador(1L);

			// VER COMO COGER UN CLIENTE
			dietaRepo.save(dieta2);

			// Dieta 3 con distinto entrenador
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
			// VER COMO COGER UN CLIENTE
			dietaRepo.save(dieta3);
		}

		
		@Nested
		@DisplayName("al consultar una dieta en concreto")
		public class ObtenerDietas {
			@Test
			@DisplayName("devuelve la dieta cuando existe")
			public void devuelveDieta() {
				var peticion = getSinQuery("http", "localhost",port, "/dieta/1",entrenador1());
				
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
				var peticion = getSinQuery("http", "localhost",port, "/dietasss/47",entrenador1());
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando alguien intenta acceder sin token")
			public void errorCuandoAccesoIndebido() {
				var peticion = getSinQuery("http", "localhost",port, "/dietas/2",null);
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
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
                    var dieta = Dieta.builder()
                            .nombre("Malagueña")
                            .build();

                    var peticion = putSinQuery("http", "localhost",port, "/dieta/1", dieta,entrenador1());

                    var respuesta = restTemplate.exchange(peticion,Dieta.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
                    Dieta dietalmacenada = dietaRepo.findById(1L).get();
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
                    var peticion = putSinQuery("http", "localhost",port, "/dietasss/50", dieta,entrenador1());

                    var respuesta = restTemplate.exchange(peticion,Void.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
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
		public class EliminarDietas {
			@Test
			@DisplayName("la elimina cuando existe")
			public void eliminarDietaCorrectamente() {
				var peticion = deleteSinQuery("http", "localhost",port, "/dieta/1",entrenador1());
				
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
				var peticion = deleteSinQuery("http", "localhost",port, "/dietasss/35",entrenador1());

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
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

		// ========================================GET /DIETAS==========================================================

		/*
		 * GET DIETAS DE UN ENTRENADOR CON DIETAS (1 O MAS)
		 * OJO -> REVISAR QUE ENTRENADOR CON ID 1 EXISTE Y TENGA DIETAS ASOCIADAS
		 * */
		@Test
		@DisplayName("devuelve la lista de dietas de un entrenador con 1 o más dietas")
		public void devuelveDietasEntrenador() {

			var peticion = get("http", "localhost", port, "/dieta", true, "1",entrenador1());

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<DietaDTO>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().size()).isGreaterThanOrEqualTo(1);
		}

		/*
		 * GET DIETAS DE UN CLIENTE CON DIETA (1)
		 * OJO -> REVISAR QUE CLIENTE CON ID = 2 EXISTE Y TENGA 1 DIETA ASOCIADA
		 * */
		@Test
		@DisplayName("devuelve la lista de dietas de un cliente con 1 dieta")
		public void devuelveDietaCliente() {

			var peticion = get("http", "localhost", port, "/dieta", false, "2",cliente4());

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().size()).isEqualTo(1);
		}

		//==============================================================================================================

		// =======================================POST /DIETAS==========================================================

		//==============================================================================================================
	}
	


}
