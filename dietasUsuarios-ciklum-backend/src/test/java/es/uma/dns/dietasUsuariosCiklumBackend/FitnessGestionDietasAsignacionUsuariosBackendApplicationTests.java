package es.uma.dns.dietasUsuariosCiklumBackend;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.UsuarioDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Cliente;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Entrenador;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Sex;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Usuario;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.ClienteRepository;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.DietaRepository;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.EntrenadorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import java.sql.Date;
import java.util.ArrayList;
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


	@Nested
	@DisplayName("cuando la base de datos esté vacía de dietas...")
	public class BaseDatosDietasVacia{
		/*
		* A COMPLETAR CON AQUELLAS FUNCIONES QUE HAGAN FALTA COMPROBAR
		* CUANDO NO EXISTE NADA EN LA BASE DE DATOS
		* */
	}

	@Nested
	@DisplayName("cuando la base de datos esté llena...")
	public class BaseDatosDietasLlena{
		/*
		* A COMPLETAR CON AQUELLAS FUNCIONES QUE HAGAN FALTA PARA
		* COMPROBAR ELEMENTOS DE LA BASE DE DATOS
		* */

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
	}

}
