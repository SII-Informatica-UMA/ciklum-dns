package es.uma.sisinfparInt.dns.jpa.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uma.sisinfparInt.dns.jpa.entities.Dieta;
import es.uma.sisinfparInt.dns.jpa.repositories.DietaRepository;
import es.uma.sisinfparInt.dns.jpa.services.excepciones.DietaExistenteException;
import es.uma.sisinfparInt.dns.jpa.services.excepciones.DietaNoEncontradaException;

@Service
@Transactional
public class DietaServicio {
    
    private DietaRepository dietaRepo;

    public DietaServicio(DietaRepository dietaRepositorio) {
        this.dietaRepo = dietaRepositorio;
    }

    //GET
    public List<Dieta> obtenerDietas() {
        return dietaRepo.findAll();
    }

    //PUT SE CORRESPONDE CON ASIGNAR, NO SABRÍA COMO MODELARLO AHORA MISMO, QUIZA NECESITE EL CLIENTE O ALGO MAS

    //POST
    public Long aniadirDieta(Dieta d) {
        if (!dietaRepo.existsByNombre(d.getNombre())) {
            d.setId(null);
            dietaRepo.save(d);
            return d.getId();
        } else {
            throw new DietaExistenteException();
        }
    }

    //GET{ID}
    public Dieta obtenerDieta(Long id) {
        var dieta = dietaRepo.findById(id);
        if (dieta.isPresent()) {
            return dieta.get();
        } else {
            throw new DietaNoEncontradaException();
        }
    }

    //PUT{ID}
    public void actualizarDieta(Dieta d) {
        if (dietaRepo.existsById(d.getId())) {
            dietaRepo.save(d);
        } else {
            throw new DietaNoEncontradaException();
        }
    }

    //DELETE{ID}
    public void eliminarDieta(Long id) {
        if (dietaRepo.existsById(id)) {
            dietaRepo.deleteById(id);
        } else {
            throw new DietaNoEncontradaException();
        }
    }

}
