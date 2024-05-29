package es.uma.dns.dietasUsuariosCiklumBackend.controllers;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.excepciones.EntidadExistenteException;
import es.uma.dns.dietasUsuariosCiklumBackend.services.DietaServicio;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<List<DietaDTO>> getDietaCliente(Long clienteId) {

        Optional<Dieta> dietaCliente = servicio.getDietaDeCliente(clienteId);

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
    private ResponseEntity<List<DietaDTO>> getDietaEntrenador(Long entrenadorId) {

        Optional<List<Dieta>> dietasEntrenador = servicio.getDietasDeEntrenador(entrenadorId);

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
    @RolesAllowed({"Cliente", "Entrenador"})
    public ResponseEntity<List<DietaDTO>> getDieta(@RequestParam(required = false) Long clienteId,
                                                   @RequestParam(required = false) Long entrenadorId) {

        if (clienteId != null && entrenadorId == null) {

            return getDietaCliente(clienteId);

        } else if (entrenadorId != null && clienteId == null) {

            return getDietaEntrenador(entrenadorId);

        } else {

            return ResponseEntity.badRequest().build();

        }
    }


    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping
    @RolesAllowed("Entrenador")
    public ResponseEntity<?> asignarDieta(@RequestParam("cliente") Long clienteId, @RequestBody DietaDTO dietaDTO) {

        boolean existeCliente = servicio.existeCliente(clienteId);

        if (existeCliente) {

            servicio.asignarDietaCliente(clienteId, Dieta.fromDietaDTO(dietaDTO));

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping
    @RolesAllowed("Entrenador")
    public ResponseEntity<?> crearDieta (@RequestParam("entrenador") Long entrenadorId, @RequestBody DietaDTO dietaDTO){

            boolean existeEntrenador = servicio.existeEntrenador(entrenadorId);

            if (existeEntrenador) {

                try {

                    Dieta dieta = Dieta.fromDietaDTO(dietaDTO);

                    Dieta dietaCreada = servicio.crearDieta(dieta, entrenadorId);

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
    @RolesAllowed({"Cliente", "Entrenador"})
    public ResponseEntity<DietaDTO> getDieta(@PathVariable Long id) {

        Optional<Dieta> dieta = servicio.getDieta(id);

        if (dieta.isPresent()) {
            // Devuelve un 200 y la dieta
            return ResponseEntity.ok(dieta.get().toDietaDTO());
        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }

        /* FORMATO FUNCIONAL
            return dieta.map(
                value
                        -> ResponseEntity.ok(value.toDietaDTO())).orElseGet(()  // Devuelve un 200 y la dieta
                        -> ResponseEntity.notFound().build()                    // Devuelve un error 404
                );
         */
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping("{id}")
    @RolesAllowed("Entrenador")
    public ResponseEntity<?> modificarDieta(@PathVariable Long id, @RequestBody DietaDTO dietaDTO) {

        Optional<Dieta> dieta = servicio.getDieta(id);

        if (dieta.isPresent()) {

            Dieta dietaModificada = Dieta.fromDietaDTO(dietaDTO);

            servicio.modificarDieta(dietaModificada);

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @DeleteMapping("{id}")
    @RolesAllowed("Entrenador")
    public ResponseEntity<?> eliminarDieta(@PathVariable Long id) {

        if (servicio.existeDieta(id)) {

            servicio.eliminarDieta(id);

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

}
