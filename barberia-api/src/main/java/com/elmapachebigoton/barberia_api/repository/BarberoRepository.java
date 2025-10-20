package com.elmapachebigoton.barberia_api.repository;

import com.elmapachebigoton.barberia_api.model.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importante añadir esta línea

@Repository
public interface BarberoRepository extends JpaRepository<Barbero, Integer> {

    /**
     * Nuevo método para encontrar todos los barberos asociados a una sucursal específica.
     * Spring Data JPA generará automáticamente la consulta SQL necesaria
     * basándose en el nombre del método.
     *
     * @param sucursalId El ID de la sucursal por la que se desea filtrar.
     * @return Una lista de barberos que pertenecen a la sucursal dada.
     */
    List<Barbero> findBySucursalId(Integer sucursalId);
}