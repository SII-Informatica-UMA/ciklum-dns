package es.uma.dns.dietasUsuariosCiklumBackend.services;

import es.uma.dns.dietasUsuariosCiklumBackend.repositories.DietaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class DietaServicio {
    
    private DietaRepository dietaRepo;

    public DietaServicio(DietaRepository dietaRepositorio) {
        this.dietaRepo = dietaRepositorio;
    }

    /*TO DO, UN METODO PARA CADA OPERACON CON LA BD QUE QUERAMOS, COMO OBTENER PRODUCTOS ETC */

}
