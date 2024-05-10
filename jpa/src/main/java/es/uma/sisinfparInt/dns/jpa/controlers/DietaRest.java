package es.uma.sisinfparInt.dns.jpa.controlers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.uma.sisinfparInt.dns.jpa.services.DietaServicio;

@RestController
@RequestMapping("/dietas")
public class DietaRest {
    private DietaServicio servicio;
    
    public DietaRest(DietaServicio serv) {
        this.servicio = serv;
    }

    /*TO DO, LOS METODOS NECESARIOS PARA CADA URL, ASI COMO IR CONSTRUYENDO LAS URLS (QUIZA PODRIAMOS USAR LA FUNCION DE CREAR URIS DEL TALLER) */

}
