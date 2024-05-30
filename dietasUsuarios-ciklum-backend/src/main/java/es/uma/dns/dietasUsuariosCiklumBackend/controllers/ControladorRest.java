package es.uma.dns.dietasUsuariosCiklumBackend.controllers;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.EntidadExistenteException;
import es.uma.dns.dietasUsuariosCiklumBackend.services.DietaServicio;
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

        Optional<Dieta> dietaCliente = servicio.getDietaDeCliente(cliente);

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

        Optional<List<Dieta>> dietasEntrenador = servicio.getDietasDeEntrenador(entrenador);

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

            if(!servicio.esCliente()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return getDietaCliente(cliente);

        } else if (entrenador != null && cliente == null) {

            if(!servicio.esEntrenador()){

                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return getDietaEntrenador(entrenador);

        } else {

            return ResponseEntity.badRequest().build();

        }
    }


    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping
    public ResponseEntity<?> asignarDieta(@RequestParam("cliente") Long cliente,
                                          @RequestBody DietaDTO dietaDTO) {

        if(!servicio.esEntrenador()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (servicio.existeCliente(cliente)) {

            servicio.asignarDietaCliente(cliente, Dieta.fromDietaDTO(dietaDTO));

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping
    public ResponseEntity<?> crearDieta (@RequestParam("entrenador") Long entrenador,
                                         @RequestBody DietaDTO dietaDTO){

        if(!servicio.esEntrenador()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (servicio.existeEntrenador(entrenador)) {

            try {

                Dieta dieta = Dieta.fromDietaDTO(dietaDTO);

                Dieta dietaCreada = servicio.crearDieta(dieta, entrenador);

                // Devuelve un 201 y la dieta creada
                return ResponseEntity.status(HttpStatus.CREATED).body(dietaCreada.toDietaDTO());

            } catch (EntidadExistenteException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping("{id}")
    public ResponseEntity<DietaDTO> getDieta(@PathVariable Long id) {

        Optional<Dieta> dieta = servicio.getDieta(id); //Hacen falta comprobaciones de seguridad

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

        if(!servicio.esEntrenador()){
          
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Dieta> dieta = servicio.getDieta(id);

        if (dieta.isPresent()) {

            Dieta dietaModificada = Dieta.fromDietaDTO(dietaDTO);

            servicio.modificarDieta(dietaModificada); //Requiere comprobaciones de seguridad

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

        if(!servicio.esEntrenador()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (servicio.existeDieta(id)) {

            servicio.eliminarDieta(id); //Requiere comprobaciones de seguridad

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

}

