package es.uma.sisinfparInt.dns.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uma.sisinfparInt.dns.jpa.entities.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
    //TO-DO//
}
