package es.uma.dns.dietasUsuariosCiklumBackend.repositories;

import java.util.List;
import java.util.Optional;

import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DietaRepository extends JpaRepository<Dieta,Integer> {
    Optional<Dieta> findById (Integer id); //Busca una dieta concreta

    List<Dieta> findAll(); //Busca todas las dietas

    Dieta save(Dieta dieta); //Guarda una dieta o actualiza una existente


    void deleteById(Integer id); //Elimina una dieta

    void deleteAll(); //Elimina todas las dietas
}