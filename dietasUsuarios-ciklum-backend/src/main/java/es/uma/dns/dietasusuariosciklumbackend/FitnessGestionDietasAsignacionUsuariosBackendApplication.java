package es.uma.dns.dietasusuariosciklumbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FitnessGestionDietasAsignacionUsuariosBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessGestionDietasAsignacionUsuariosBackendApplication.class, args);
	}

	@Bean
	public RestTemplate generaRestTemplate() {
		return new RestTemplate();
	}

}
