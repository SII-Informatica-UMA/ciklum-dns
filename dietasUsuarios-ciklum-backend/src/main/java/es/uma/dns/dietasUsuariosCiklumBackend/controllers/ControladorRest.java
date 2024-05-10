package es.uma.dns.dietasUsuariosCiklumBackend.controllers;

import es.uma.dns.dietasUsuariosCiklumBackend.dtos.DietaDTO;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Cliente;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Dieta;
import es.uma.dns.dietasUsuariosCiklumBackend.entities.Entrenador;
import es.uma.dns.dietasUsuariosCiklumBackend.services.ServicioFalso;
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

    private ServicioFalso servicioFalso;

    @Autowired
    public ControladorRest(ServicioFalso servicioFalso) {
        this.servicioFalso = servicioFalso;
    }

    //FALTA ERROR 403 comprobando que quien hace la peticion es el mismo que el parametro de entrada !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping
    public ResponseEntity<List<DietaDTO>> getDietaCliente(@RequestParam("cliente") Long clienteId) {

        Optional<Cliente> cliente = servicioFalso.getCliente(clienteId);

        if (cliente.isPresent()) {

            Dieta dieta = cliente.get().getDieta();

            if (dieta == null) {
                // Devuelve un error 404
                return ResponseEntity.notFound().build();
            } else {
                List<DietaDTO> dietasDTO = new ArrayList<>();
                dietasDTO.add(dieta.toDietaDTO());
                // Devuelve un 200
                return ResponseEntity.ok(dietasDTO);
            }

        } else {
            // Devuelve un error 403
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //FALTA ERROR 403 comprobando que quien hace la peticion es el mismo que el parametro de entrada !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping
    public ResponseEntity<List<DietaDTO>> getDietaEntrenador(@RequestParam("entrenador") Long entrenadorId) {

        Optional<Entrenador> entrenador = servicioFalso.getEntrenador(entrenadorId);

        if (entrenador.isPresent()) {

            List<DietaDTO> dietasDTO = entrenador.get().getDietas().stream()
                    .map(Dieta::toDietaDTO)
                    .collect(Collectors.toList());

            if (dietasDTO.isEmpty()) {
                // Devuelve un error 404
                return ResponseEntity.notFound().build();
            } else {
                // Devuelve un 200
                return ResponseEntity.ok(dietasDTO);
            }

        } else {
            // Devuelve un error 403
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping
    public ResponseEntity<?> asignarDieta(@RequestParam("cliente") Long clienteId, @RequestBody DietaDTO dietaDTO) {

        Optional<Cliente> cliente = servicioFalso.getCliente(clienteId);

        if (cliente.isPresent()) {

            servicioFalso.modificarDietaCliente(clienteId, Dieta.fromDietaDTO(dietaDTO));

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping
    public ResponseEntity<?> crearDieta (@RequestParam("entrenador") Long entrenadorId, @RequestBody DietaDTO dietaDTO){

            Optional<Entrenador> entrenador = servicioFalso.getEntrenador(entrenadorId);

            if (entrenador.isPresent()) {

                Dieta dieta = Dieta.fromDietaDTO(dietaDTO);
                dieta.setEntrenador(entrenador.get());

                Dieta dietaCreada = servicioFalso.crearDieta(dieta);

                // Devuelve un 201 y la dieta creada
                return ResponseEntity.status(HttpStatus.CREATED).body(dietaCreada.toDietaDTO());

            } else {
                // Devuelve un error 404
                return ResponseEntity.notFound().build();
            }
    }

    //FALTA ERROR 403 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping("{id}")
    public ResponseEntity<DietaDTO> getDieta(@PathVariable Long id) {

        Optional<Dieta> dieta = servicioFalso.getDieta(id);

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
    public ResponseEntity<?> modificarDieta(@PathVariable Long id, @RequestBody DietaDTO dietaDTO) {

        Optional<Dieta> dieta = servicioFalso.getDieta(id);

        if (dieta.isPresent()) {

            Dieta dietaModificada = Dieta.fromDietaDTO(dietaDTO);
            dietaModificada.setId(id);

            servicioFalso.modificarDieta(dietaModificada);

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

        if (servicioFalso.existsDieta(id)) {

            servicioFalso.eliminarDieta(id);

            // Devuelve un 200
            return ResponseEntity.ok().build();

        } else {
            // Devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

}
