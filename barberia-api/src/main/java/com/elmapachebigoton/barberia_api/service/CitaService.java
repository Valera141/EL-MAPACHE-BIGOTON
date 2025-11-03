package com.elmapachebigoton.barberia_api.service;

import com.elmapachebigoton.barberia_api.model.Cita;
import com.elmapachebigoton.barberia_api.repository.BarberoRepository;
import com.elmapachebigoton.barberia_api.repository.CitaRepository;
import com.elmapachebigoton.barberia_api.repository.ClienteRepository;
import com.elmapachebigoton.barberia_api.repository.ServicioRepository;
import com.elmapachebigoton.barberia_api.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elmapachebigoton.barberia_api.dto.CitaRequestDTO;
import com.elmapachebigoton.barberia_api.model.Barbero;
import com.elmapachebigoton.barberia_api.model.Cliente;
import com.elmapachebigoton.barberia_api.model.Servicio;
import com.elmapachebigoton.barberia_api.model.Sucursal;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

@Service // 1. Marcamos esta clase como un Servicio de Spring
public class CitaService {

    // 2. Inyectamos todos los repositorios aquí, en lugar del controlador
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    // 3. Métodos para obtener citas (lógica simple, por ahora)
    public Iterable<Cita> findAll() {
        return citaRepository.findAll();
    }

    public Optional<Cita> findById(Integer id) {
        return citaRepository.findById(id);
    }

    // 4. Mantenemos la lógica de validación y creación aquí
public Cita create(CitaRequestDTO citaDTO) {

        // 1. Buscamos las entidades relacionadas.
        // .orElseThrow() es la nueva validación. Si no lo encuentra, lanza una excepción.
        Cliente cliente = clienteRepository.findById(citaDTO.getCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        Barbero barbero = barberoRepository.findById(citaDTO.getBarbero().getId())
                .orElseThrow(() -> new EntityNotFoundException("Barbero no encontrado"));

        Servicio servicio = servicioRepository.findById(citaDTO.getServicio().getId())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        Sucursal sucursal = sucursalRepository.findById(citaDTO.getSucursal().getId())
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));

        // 2. Creamos la nueva entidad Cita
        Cita nuevaCita = new Cita();
        nuevaCita.setFechaHora(citaDTO.getFechaHora());
        nuevaCita.setCliente(cliente);
        nuevaCita.setBarbero(barbero);
        nuevaCita.setServicio(servicio);
        nuevaCita.setSucursal(sucursal);

        // 3. Guardamos la entidad
        return citaRepository.save(nuevaCita);
    }

    /**
     * MODIFICADO: Ahora acepta un DTO
     */
    public Optional<Cita> update(Integer id, CitaRequestDTO citaDTO) {
        if (!citaRepository.findById(id).isPresent()) {
            return Optional.empty(); // No se encontró la cita
        }

        // 1. Buscamos las entidades relacionadas (Validación)
        Cliente cliente = clienteRepository.findById(citaDTO.getCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        Barbero barbero = barberoRepository.findById(citaDTO.getBarbero().getId())
                .orElseThrow(() -> new EntityNotFoundException("Barbero no encontrado"));

        Servicio servicio = servicioRepository.findById(citaDTO.getServicio().getId())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        Sucursal sucursal = sucursalRepository.findById(citaDTO.getSucursal().getId())
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));

        // 2. Creamos la entidad actualizada
        Cita citaActualizada = new Cita();
        citaActualizada.setId(id); // Importante: seteamos el ID para que JPA sepa que es un UPDATE
        citaActualizada.setFechaHora(citaDTO.getFechaHora());
        citaActualizada.setCliente(cliente);
        citaActualizada.setBarbero(barbero);
        citaActualizada.setServicio(servicio);
        citaActualizada.setSucursal(sucursal);

        return Optional.of(citaRepository.save(citaActualizada));
    }
    // 6. Lógica de borrado
    public boolean deleteById(Integer id) {
        if (!citaRepository.findById(id).isPresent()) {
            return false; // No se encontró
        }
        citaRepository.deleteById(id);
        return true; // Se borró exitosamente
    }
}