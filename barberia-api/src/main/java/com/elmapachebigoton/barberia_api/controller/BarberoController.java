package com.elmapachebigoton.barberia_api.controller;

import com.elmapachebigoton.barberia_api.model.Barbero;
import com.elmapachebigoton.barberia_api.repository.BarberoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/barberos")
public class BarberoController {

    @Autowired
    private BarberoRepository barberoRepository;

    /**
     * Endpoint modificado para obtener barberos.
     * Puede filtrar por sucursal si se proporciona el parámetro 'sucursalId'.
     * Si no se proporciona, devuelve todos los barberos.
     *
     * @param sucursalId Parámetro opcional para filtrar por el ID de la sucursal.
     * @return Una lista de barberos.
     */
    @GetMapping
    public ResponseEntity<Iterable<Barbero>> findAll(@RequestParam Optional<Integer> sucursalId) {
        if (sucursalId.isPresent()) {
            // Si el ID de la sucursal está presente, usamos el nuevo método del repositorio
            return ResponseEntity.ok(barberoRepository.findBySucursalId(sucursalId.get()));
        } else {
            // Si no, devolvemos todos los barberos como antes
            return ResponseEntity.ok(barberoRepository.findAll());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barbero> findById(@PathVariable Integer id) {
        Optional<Barbero> barbero = barberoRepository.findById(id);
        return barbero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Los métodos POST, PUT y DELETE se mantienen sin cambios ---

    @PostMapping
    public ResponseEntity<Barbero> create(@RequestBody Barbero barbero, UriComponentsBuilder uriBuilder) {
        // Para crear un barbero, el JSON de entrada ahora debe incluir la sucursal.
        // Ejemplo: { "nombre": "Juan", "fotoUrl": "...", "sucursal": { "id": 1 } }
        Barbero created = barberoRepository.save(barbero);
        URI uri = uriBuilder.path("/barberos/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Barbero barbero) {
        if (!barberoRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        barbero.setId(id);
        barberoRepository.save(barbero);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!barberoRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        barberoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}