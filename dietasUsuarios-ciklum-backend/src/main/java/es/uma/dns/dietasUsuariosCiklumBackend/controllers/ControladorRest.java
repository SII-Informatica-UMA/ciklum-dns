package es.uma.dns.dietasUsuariosCiklumBackend.controllers;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaNuevaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.ArgumentoMaloException;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.EntidadExistenteException;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.EntidadNoEncontradaException;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.PermisosInsuficientesException;
import es.uma.dns.dietasUsuariosCiklumBackend.services.DietaServicio;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.uma.dns.dietasUsuariosCiklumBackend.security.SecurityConfguration;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dieta")
public class ControladorRest {

    private DietaServicio servicio;

    @Autowired
    public ControladorRest(DietaServicio servicio) {
        this.servicio = servicio;
    }

    private ResponseEntity<List<DietaDTO>> getDietaCliente(Long cliente) {

        Optional<Dieta> dietaCliente = null;

        if (cliente == null){ //Bad request, no se puso id
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } 

        try {
            dietaCliente = servicio.getDietaDeCliente(cliente);
        } catch (PermisosInsuficientesException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (dietaCliente.isPresent()) {

            List<DietaDTO> dietasDTO = new ArrayList<>();
            dietasDTO.add(dietaCliente.get().toDietaDTO());
            // Devuelve un 200
            return ResponseEntity.ok(dietasDTO);

        } else {
            //devuelve 404
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<List<DietaDTO>> getDietaEntrenador(Long entrenador) {

        Optional<List<Dieta>> dietasEntrenador = null;
        try {
            dietasEntrenador = servicio.getDietasDeEntrenador(entrenador);
        } catch (PermisosInsuficientesException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (dietasEntrenador.isPresent()) {

            List<DietaDTO> dietasDTO = dietasEntrenador
                    .get()
                    .stream()
                    .map(Dieta::toDietaDTO)
                    .collect(Collectors.toList());

            // Devuelve un 200
            return ResponseEntity.ok(dietasDTO);

        } else {
            //devuelve 404
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<DietaDTO>> getDieta(@RequestParam(required = false) Long cliente,
                                                   @RequestParam(required = false) Long entrenador) {


        if (cliente != null && entrenador == null) {

            return getDietaCliente(cliente);

        } else if (entrenador != null && cliente == null) {

            return getDietaEntrenador(entrenador);

        } else {

            return ResponseEntity.badRequest().build();

        }
    }


    @PutMapping
    public ResponseEntity<?> asignarDieta(@RequestParam("cliente") Long cliente,
                                          @RequestBody DietaDTO dietaDTO) {
        

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (servicio.existeCliente(cliente)) {
            try {
                servicio.asignarDietaCliente(cliente, Dieta.fromDietaDTO(dietaDTO));

                return ResponseEntity.ok().build();
                // Devuelve un 200
            } catch (PermisosInsuficientesException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearDieta (@RequestParam("entrenador") Long entrenador,
                                         @RequestBody DietaNuevaDTO dietaDTO){

        if (entrenador == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (servicio.existeEntrenador(entrenador)) {

            try {

                Dieta dieta = Dieta.fromDietaNuevaDTO(dietaDTO, entrenador);

                Dieta dietaCreada = servicio.crearDieta(dieta, entrenador);

                URI location = servicio.uriDeDieta(dietaCreada.getId());

                // Devuelve un 201 y la dieta creada
                return ResponseEntity.created(location).body(dietaCreada);

            } catch (EntidadExistenteException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } catch (PermisosInsuficientesException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DietaDTO> getDieta(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Dieta> dieta = null; //Hacen falta comprobaciones de seguridad

        try {
            dieta = servicio.getDieta(id);
        } catch (PermisosInsuficientesException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ArgumentoMaloException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

        if (!dieta.isEmpty()) {
            // Devuelve un 200 y la dieta
            return ResponseEntity.ok(dieta.get().toDietaDTO());
        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> modificarDieta(@PathVariable Long id,
                                            @RequestBody DietaDTO dietaDTO) {

        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Dieta> dieta = null;
        try {
            dieta = servicio.getDieta(id);
        } catch (PermisosInsuficientesException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ArgumentoMaloException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

        if (dieta.isPresent()) {

            Dieta dietaModificada = Dieta.fromDietaDTO(dietaDTO);
            try {
                servicio.modificarDieta(dietaModificada); //Requiere comprobaciones de seguridad
            } catch (PermisosInsuficientesException e){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarDieta(@PathVariable Long id) {

        if (id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (servicio.existeDieta(id)) {
            try {
                servicio.eliminarDieta(id); //Requiere comprobaciones de seguridad
            } catch (PermisosInsuficientesException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

}

