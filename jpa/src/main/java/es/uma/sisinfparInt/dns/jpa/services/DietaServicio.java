package es.uma.sisinfparInt.dns.jpa.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uma.sisinfparInt.dns.jpa.repositories.DietaRepository;

@Service
@Transactional
public class DietaServicio {
    
    private DietaRepository dietaRepo;

    public DietaServicio(DietaRepository dietaRepositorio) {
        this.dietaRepo = dietaRepositorio;
    }

    /*TO DO, UN METODO PARA CADA OPERACON CON LA BD QUE QUERAMOS, COMO OBTENER PRODUCTOS ETC */

}
