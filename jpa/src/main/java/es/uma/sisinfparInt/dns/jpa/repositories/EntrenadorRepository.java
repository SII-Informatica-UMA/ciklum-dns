package es.uma.dns.dietasUsuariosCiklumBackend.repositories;

import java.util.List;
import java.util.Optional;

import es.uma.dns.dietasUsuariosCiklumBackend.entities.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EntrenadorRepository extends JpaRepository<Entrenador,Integer> {

    Optional<Entrenador> findById(Integer id); //Busca un entrenador

    List<Entrenador> findAll(); //Busca todos los entrenadores

    Entrenador save(Entrenador entrenador); //Guarda o actualiza un entrenador

    void deleteById(Integer id); //Borra un entrenador

    void deleteAll(); //Borra todos los entrenadores

}
