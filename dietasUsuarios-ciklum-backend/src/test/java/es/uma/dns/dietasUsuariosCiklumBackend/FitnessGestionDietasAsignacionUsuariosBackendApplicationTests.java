package es.uma.dns.dietasUsuariosCiklumBackend;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.DietaRepository;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.EntrenadorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName(" En el servicio de dietas...")
@DirtiesContext(classMode =  DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FitnessGestionDietasAsignacionUsuariosBackendApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private DietaRepository dietaRepo;
	@Autowired
	private EntrenadorRepository entrenadorRepo;

	@Autowired
	private ClienteRepository clienteRepo;
	

	private URI uri(String scheme, String host, int port, String ...paths) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub = ubf.builder()
				.scheme(scheme)
				.host(host).port(port);
		for (String path: paths) {
			ub = ub.path(path);
		}
		return ub.build();
	}

	private RequestEntity<Void> get(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.build();
		return peticion;
	}

	private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.delete(uri)
				.build();
		return peticion;
	}

	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.put(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
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

	/*
	 * A COMPLETAR CON AQUELLAS FUNCIONES QUE HAGAN FALTA COMPROBAR
	 * CUANDO NO EXISTE NADA EN LA BASE DE DATOS
	 * */
	@Nested
	@DisplayName("cuando la base de datos esté vacía de dietas...")
	public class BaseDatosDietasVacia{

		// ========================================GET /DIETAS==========================================================

		/*
		 * GET DIETAS DE UN ENTRENADOR VACIO
		 * OJO -> REVISAR QUE ENTRENADOR CON ID 1 EXISTE
		 * */
		@Test
		@DisplayName("devuelve la lista de dietas de un entrenador vacía")
		public void devuelveDietasVaciaEntrenador() {

			var peticion = get("http", "localhost", port, "/dietas?entrenador=1");

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

			var peticion = get("http", "localhost", port, "/dietas?cliente=2");

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

			var peticion = get("http", "localhost", port, "/dietas?entrenador=100000000");

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

			var peticion = get("http", "localhost", port, "/dietas?cliente=100000000");

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

			var peticion = get("http", "localhost", port, "/dietas");

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

			var peticion = get("http", "localhost", port, "/dietas?entrenador=pepito");

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

			var peticion = get("http", "localhost", port, "/dietas?cliente=pepito");

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


			var peticion = post("http", "localhost",port, "/dietas?entrenador=1", dieta);

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

			assertThat(dietaEntidad.getEntrenador().getId()).isEqualTo(1L);
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


			var peticion = post("http", "localhost",port, "/dietas?entrenador=1000000000", dieta);

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


			var peticion = post("http", "localhost",port, "/dietas", dieta);

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


			var peticion = post("http", "localhost",port, "/dietas?entrenador=pepe", dieta);

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

		@BeforeEach
		public void insertarDatos(){
			
			//Entrenador 1
			Date a=new Date(111111);
			var entrenador1= new Entrenador();
			entrenador1.setId(1L);
			entrenador1.setAdministrador(false);
			entrenador1.setApellido1("carr");
			entrenador1.setApellido2("carr");
			entrenador1.setEmail("ejemplo@gmail.com");
			entrenador1.setNombre("Luis");
			entrenador1.setPassword("122");
			entrenador1.setFechaNacimiento(a);
			entrenador1.setFechaAlta(a);
			entrenador1.setFechaBaja(a);
			entrenador1.setTelefono("111199999");
			entrenador1.setDireccion("calle ejemplo 26");
			entrenador1.setDni("00000000A");
			entrenador1.setEspecialidad("brazos");
			entrenador1.setTitulacion("ninguna");
			entrenador1.setExperiencia("12");
			entrenador1.setObservaciones("muy ruidoso");
			entrenador1.setIdCentro(1L);

			entrenadorRepo.save(entrenador1);

			//Entrenador 2
			Date b=new Date(222222);
			var entrenador2= new Entrenador();
			entrenador1.setId(2L);
			entrenador1.setAdministrador(false);
			entrenador1.setApellido1("call");
			entrenador1.setApellido2("call");
			entrenador1.setEmail("ejemplo2@gmail.com");
			entrenador1.setNombre("Pepe");
			entrenador1.setPassword("123");
			entrenador1.setFechaNacimiento(b);
			entrenador1.setFechaAlta(b);
			entrenador1.setFechaBaja(b);
			entrenador1.setTelefono("444499999");
			entrenador1.setDireccion("calle ejemplo2 36");
			entrenador1.setDni("00000001A");
			entrenador1.setEspecialidad("piernas");
			entrenador1.setTitulacion("podología");
			entrenador1.setExperiencia("1");
			entrenador1.setObservaciones("muy energico");
			entrenador1.setIdCentro(1L);

			entrenadorRepo.save(entrenador2);
			
			
			
			
			// Dieta 1
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
			// VER COMO COGER UN ENTRENADOR-> id=1L
			dieta1.setEntrenador(entrenador1);
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
			// dieta2.setEntrenador();

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
			// VER COMO COGER UN ENTRENADOR-> id=2L
			dieta2.setEntrenador(entrenador1);

			// VER COMO COGER UN CLIENTE
			dietaRepo.save(dieta3);


			//cliente 1
			
			var cliente1= new Cliente();
			cliente1.setId(3L);
			cliente1.setNombre("Juan");
			cliente1.setApellido1("ap");
			cliente1.setApellido2("ap2");
			cliente1.setAdministrador(false);
			cliente1.setDieta(dieta1);
			cliente1.setEmail("ejemplocli@gmail.com");
			cliente1.setFechaNacimiento(b);
			cliente1.setPassword("111");
			cliente1.setTelefono("222244442");
			cliente1.setDireccion("calle ejemplo 35");
			cliente1.setDni("22222222G");
			cliente1.setSexo(Sex.HOMBRE);
			clienteRepo.save(cliente1);

			//cliente 2
			
			var cliente2= new Cliente();
			cliente1.setId(4L);
			cliente1.setNombre("Juana");
			cliente1.setApellido1("ape");
			cliente1.setApellido2("ape2");
			cliente1.setAdministrador(false);
			cliente1.setDieta(dieta2);
			cliente1.setEmail("ejemplocliente@gmail.com");
			cliente1.setFechaNacimiento(a);
			cliente1.setPassword("112");
			cliente1.setTelefono("222245442");
			cliente1.setDireccion("calle ejemplo 55");
			cliente1.setDni("22222223F");
			cliente1.setSexo(Sex.MUJER);
			clienteRepo.save(cliente2);
			
		}

		
		@Nested
		@DisplayName("al consultar una dieta en concreto")
		public class ObtenerDietas {
			@Test
			@DisplayName("devuelve la dieta cuando existe")
			public void devuelveDieta() {
				var peticion = get("http", "localhost",port, "/dieta/1");
				
				var respuesta = restTemplate.exchange(peticion, DietaDTO.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				assertThat(respuesta.hasBody()).isTrue();
				assertThat(respuesta.getBody()).isNotNull();
			}
			
			@Test
			@DisplayName("da error cuando no existe la dieta")
			public void errorCuandoDietaNoExiste() {
				var peticion = get("http", "localhost",port, "/dieta/47");
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando no se realiza una bad request")
			public void errorCuandoBadRequest() {
				var peticion = get("http", "localhost",port, "/dietasss/47");
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<DietaDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			//FALTA EL 403 : ACCESO NO AUTORIZADO
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

                    var peticion = put("http", "localhost",port, "/dieta/1", dieta);

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
                    var peticion = put("http", "localhost",port, "/dieta/50", dieta);

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
                    var peticion = put("http", "localhost",port, "/dietasss/50", dieta);

                    var respuesta = restTemplate.exchange(peticion,Void.class);

                    assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
                    assertThat(respuesta.hasBody()).isEqualTo(false);
                }

				//FALTA EL 403 : ACCESO NO AUTORIZADO
            }

		@Nested
		@DisplayName("al eliminar una dieta")
		public class EliminarDietas {
			@Test
			@DisplayName("la elimina cuando existe")
			public void eliminarDietaCorrectamente() {
				var peticion = delete("http", "localhost",port, "/dieta/1");
				
				var respuesta = restTemplate.exchange(peticion,Void.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				List<Dieta> dietas = dietaRepo.findAll();
				assertThat(dietas).hasSize(2);
				assertThat(dietas).allMatch(c->c.getId()!=1);
			}
			
			@Test
			@DisplayName("da error cuando la dieta no existe")
			public void errorCuandoDietaNoExiste() {
				var peticion = delete("http", "localhost",port, "/dieta/50");
				
				var respuesta = restTemplate.exchange(peticion,Void.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando se hace una bad request")
			public void errorCuandoBadRequest() {
				var peticion = delete("http", "localhost",port, "/dietasss/35");

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			//FALTA EL 403 : ACCESO NO AUTORIZADO
		}

		// ========================================GET /DIETAS==========================================================

		/*
		 * GET DIETAS DE UN ENTRENADOR CON DIETAS (1 O MAS)
		 * OJO -> REVISAR QUE ENTRENADOR CON ID 1 EXISTE Y TENGA DIETAS ASOCIADAS
		 * */
		@Test
		@DisplayName("devuelve la lista de dietas de un entrenador con 1 o más dietas")
		public void devuelveDietasEntrenador() {

			var peticion = get("http", "localhost", port, "/dietas?entrenador=1");

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

			var peticion = get("http", "localhost", port, "/dietas?cliente=2");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Dieta>>() {
					});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().size()).isEqualTo(1);
		}

		//==============================================================================================================

		// =======================================POST /DIETAS==========================================================
		/*
		 *  ERROR POST DIETAS NOMBRE EXISTENTE
		 * */
		@Test
		@DisplayName(" da error insertando una dieta por tener nombre existente")
		public void errorInsertaDietaNombreExistente() {
			List<String> alimentos = new ArrayList<>();
			alimentos.add("tomate");
			var dieta = DietaNuevaDTO.builder()
					.nombre("Mediterranea")
					.descripcion("descripcion")
					.objetivo("objtv")
					.observaciones("observc")
					.duracionDias(1)
					.alimentos(alimentos)
					.recomendaciones("recomendaciones")
					.build();


			var peticion = post("http", "localhost",port, "/dietas?entrenador=1", dieta);

			var respuesta = restTemplate.exchange(peticion,Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
		}
		//==============================================================================================================
	}
	


}
