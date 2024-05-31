package es.uma.dns.dietasUsuariosCiklumBackend.controllers;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
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

    //FALTA ERROR 403 comprobando que quien hace la peticion es el mismo que el parametro de entrada !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private ResponseEntity<List<DietaDTO>> getDietaCliente(Long cliente) {

        Optional<Dieta> dietaCliente = null;
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
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    //FALTA ERROR 403 comprobando que quien hace la peticion es el mismo que el parametro de entrada !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
            // Devuelve un error 404
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


    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping
    public ResponseEntity<?> asignarDieta(@RequestParam("cliente") Long cliente,
                                          @RequestBody DietaDTO dietaDTO) {

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

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping
    public ResponseEntity<?> crearDieta (@RequestParam("entrenador") Long entrenador,
                                         @RequestBody DietaDTO dietaDTO){

        if (servicio.existeEntrenador(entrenador)) {

            try {

                Dieta dieta = Dieta.fromDietaDTO(dietaDTO);

                Dieta dietaCreada = servicio.crearDieta(dieta, entrenador);

                // Devuelve un 201 y la dieta creada
                return ResponseEntity.status(HttpStatus.CREATED).body(dietaCreada.toDietaDTO());

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

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping("{id}")
    public ResponseEntity<DietaDTO> getDieta(@PathVariable Long id) {

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

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping("{id}")
    public ResponseEntity<?> modificarDieta(@PathVariable Long id,
                                            @RequestBody DietaDTO dietaDTO) {

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

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarDieta(@PathVariable Long id) {

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

