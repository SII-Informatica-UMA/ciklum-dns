package es.uma.sisinfparInt.dns.jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uma.sisinfparInt.dns.jpa.entities.Dieta;

public interface DietaRepository extends JpaRepository<Dieta,Integer> {
    Optional<Dieta> findById (Integer id); //Busca una dieta concreta

    List<Dieta> findAll(); //Busca todas las dietas

    Dieta save(Dieta dieta); //Guarda una dieta o actualiza una existente

    List<Dieta> saveAll(List<Dieta> dietas); //Guarda toda una lista de dietas o la actualiza

    void deleteById(Integer id); //Elimina una dieta

    void deleteAll(); //Elimina todas las dietas
}