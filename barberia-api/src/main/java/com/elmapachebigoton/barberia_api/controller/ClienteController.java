package com.elmapachebigoton.barberia_api.controller;

import com.elmapachebigoton.barberia_api.model.Cliente;
import com.elmapachebigoton.barberia_api.service.ClienteService; // 1. Importa el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService; // 2. Inyecta el servicio

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint original para crear.
     * Lo mantenemos por si se usa en otro lugar, pero /findOrCreate es preferido.
     */
    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente, UriComponentsBuilder uriBuilder) {
        Cliente created = clienteService.create(cliente);
        URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // 3. ¡NUEVO ENDPOINT!
    /**
     * Busca un cliente por teléfono. Si no existe, lo crea.
     * Este es el endpoint que debe usar el formulario de reserva de citas.
     */
    @PostMapping("/findOrCreate")
    public ResponseEntity<Cliente> findOrCreate(@RequestBody Cliente cliente) {
        try {
            Cliente clienteEncontradoOCreado = clienteService.findOrCreateCliente(cliente);
            // Devolvemos 200 OK si lo encontró, o 201 CREATED si lo acaba de crear.
            // Para simplificar, podemos devolver siempre 200 OK con el cuerpo.
            // O podemos ser más precisos:
            if (clienteEncontradoOCreado.getId().equals(cliente.getId())) {
                return ResponseEntity.ok(clienteEncontradoOCreado); // Ya existía
            } else {
                 // Acaba de ser creado (o es uno diferente al que se envió)
                 // Para ser simples, devolvamos 200 OK
                 return ResponseEntity.ok(clienteEncontradoOCreado);
            }
             
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // O un objeto de error
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteService.update(id, cliente)
                .map(c -> ResponseEntity.ok().<Void>build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!clienteService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}