package com.elmapachebigoton.barberia_api.repository;

import com.elmapachebigoton.barberia_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Asegúrate de importar Optional

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByTelefono(String telefono);
}