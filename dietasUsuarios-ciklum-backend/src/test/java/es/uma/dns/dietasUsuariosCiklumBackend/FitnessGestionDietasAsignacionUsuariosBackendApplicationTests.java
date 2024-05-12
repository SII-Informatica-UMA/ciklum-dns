package es.uma.dns.dietasUsuariosCiklumBackend;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Entrenador;
import es.uma.dns.dietasUsuariosCiklumBackend.repositories.DietaRepository;

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
			// Dieta 1
			var dieta1 = new Dieta();
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
			// dieta1.setEntrenador();

			// VER COMO COGER UN CLIENTE
			dietaRepo.save(dieta1);

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
			// dieta2.setEntrenador();

			// VER COMO COGER UN CLIENTE
			dietaRepo.save(dieta3);
		}

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
	}
	


}
