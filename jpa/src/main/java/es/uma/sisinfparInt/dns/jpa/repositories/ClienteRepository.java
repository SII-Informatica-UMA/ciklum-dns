package es.uma.sisinfparInt.dns.jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uma.sisinfparInt.dns.jpa.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    Optional<Cliente> findById(Integer id); //Busca un cliente por una id

    List<Cliente> findAll(); //Busca todos los clientes

    Cliente save(Cliente cliente); //Guarda o actualiza un cliente

    void deleteById(Integer id); //Borra un cliente segun la id

    void deleteAll(); //Borra todos los clientes
}
