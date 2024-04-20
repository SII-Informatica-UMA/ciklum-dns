package es.uma.sisinfparInt.dns.jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uma.sisinfparInt.dns.jpa.entities.Entrenador;

public interface EntrenadorRepository extends JpaRepository<Entrenador,Integer> {
    Optional<Entrenador> findById(Integer id); //Busca un entrenador

    List<Entrenador> findAll(); //Busca todos los entrenadores

    Entrenador save(Entrenador entrenador); //Guarda o actualiza un entrenador

    List<Entrenador> saveAll(); //Guarda o actualiza todos los entrenadores

    void deleteById(Integer id); //Borra un entrenador

    void deleteAll(); //Borra todos los entrenadores
}
