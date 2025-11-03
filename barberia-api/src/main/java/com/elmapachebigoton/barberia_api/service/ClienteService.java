package com.elmapachebigoton.barberia_api.service;

import com.elmapachebigoton.barberia_api.model.Cliente;
import com.elmapachebigoton.barberia_api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Iterable<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    /**
     * Lógica "Buscar o Crear".
     * Busca un cliente por teléfono. Si existe, lo devuelve.
     * Si no existe, lo crea con los datos proporcionados y lo devuelve.
     */
    public Cliente findOrCreateCliente(Cliente clienteData) {
        // 1. Buscar por teléfono (asegurándonos de que no sea nulo)
        String telefono = clienteData.getTelefono();
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }

        Optional<Cliente> clienteExistente = clienteRepository.findByTelefono(telefono);

        if (clienteExistente.isPresent()) {
            // 2. Si existe, lo devolvemos
            return clienteExistente.get();
        } else {
            // 3. Si no existe, creamos uno nuevo y lo guardamos
            // Aseguramos que el nombre no sea nulo
            if (clienteData.getNombre() == null || clienteData.getNombre().trim().isEmpty()) {
                 throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            // Creamos una nueva entidad para asegurarnos de que no tenga un ID
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(clienteData.getNombre());
            nuevoCliente.setTelefono(clienteData.getTelefono());
            return clienteRepository.save(nuevoCliente);
        }
    }

    public Cliente create(Cliente cliente) {
        // Este método ahora es menos usado, pero lo mantenemos por consistencia
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> update(Integer id, Cliente cliente) {
        if (!clienteRepository.findById(id).isPresent()) {
            return Optional.empty();
        }
        cliente.setId(id);
        return Optional.of(clienteRepository.save(cliente));
    }

    public boolean deleteById(Integer id) {
        if (!clienteRepository.findById(id).isPresent()) {
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }
}