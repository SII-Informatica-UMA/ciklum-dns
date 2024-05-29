package es.uma.dns.dietasUsuariosCiklumBackend.repositories;

import java.util.List;
import java.util.Optional;

import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DietaRepository extends JpaRepository<Dieta,Long> {

    boolean existsByNombre(String nombre); //Comprueba si existe la dieta dado un nombre

    Optional<Dieta> findByNombre(String nombre); //Devuelve una dieta dado su nombre

    Optional<Dieta> findByEntrenador(Long id); //Devuelve una dieta dada la id de su entrenador

    Optional<Dieta> findByClientes(List<Long> clientes); //Devuelve una dieta dada la id de los clientes que la tienen

}