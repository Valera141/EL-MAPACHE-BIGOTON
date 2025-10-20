package com.elmapachebigoton.barberia_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "barbero")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Barbero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String fotoUrl;

    /**
     * Relación con Sucursal:
     * Un barbero pertenece a una única sucursal.
     * FetchType.LAZY: La sucursal solo se cargará de la base de datos cuando se acceda a ella explícitamente.
     * JsonIgnoreProperties: Evita problemas de serialización en bucle al convertir a JSON.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sucursal sucursal;
}