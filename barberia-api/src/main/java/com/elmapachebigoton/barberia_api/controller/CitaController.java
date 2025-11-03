package com.elmapachebigoton.barberia_api.controller;

import com.elmapachebigoton.barberia_api.model.Cita;
// 1. Importa el nuevo servicio
import com.elmapachebigoton.barberia_api.service.CitaService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.elmapachebigoton.barberia_api.dto.CitaRequestDTO; 
import jakarta.persistence.EntityNotFoundException;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    // 2. ¡Ahora solo inyectamos UNA cosa: el servicio!
    @Autowired
    private CitaService citaService;

    // 3. Los métodos del controlador ahora son muy simples, solo llaman al servicio.
    @GetMapping
    public ResponseEntity<Iterable<Cita>> findAll() {
        return ResponseEntity.ok(citaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> findById(@PathVariable Integer id) {
        Optional<Cita> cita = citaService.findById(id);
        return cita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

   @PostMapping
    public ResponseEntity<Cita> create(@RequestBody CitaRequestDTO citaDTO, UriComponentsBuilder uriBuilder) {
        try {
            Cita created = citaService.create(citaDTO);
            URI uri = uriBuilder.path("/citas/{id}").buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uri).body(created);
        } catch (EntityNotFoundException e) {
            // Si el servicio no encontró un ID, respondemos 422 Unprocessable Entity
            return ResponseEntity.unprocessableEntity().build(); 
        }
    }

   @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody CitaRequestDTO citaDTO) {
        try {
            Optional<Cita> updatedCita = citaService.update(id, citaDTO);
            
            if (!updatedCita.isPresent()) {
                 return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!citaService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}