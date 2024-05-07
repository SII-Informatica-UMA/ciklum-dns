package es.uma.sisinfparInt.dns.jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uma.sisinfparInt.dns.jpa.entities.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    Optional<Usuario> findById(Long id); //Busca un usuario

    List<Usuario> findAll(); //Busca todos los usuarios

    Usuario save(Usuario usuario); //Guarda o actualiza un usuario


    void deleteById(Long id); //Borra un usuario

    void deleteAll(); //Borra todos los usuarios
}
