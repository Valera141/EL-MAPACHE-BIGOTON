package com.elmapachebigoton.barberia_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // --- CAMBIOS AQUÍ ---
    // Se eliminó (fetch = FetchType.EAGER)
    @ManyToOne 
    @JoinColumn(name="id_cliente", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;

    // Se eliminó (fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name="id_barbero", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Barbero barbero;

    // Se eliminó (fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name="id_servicio", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Servicio servicio;
    
    /**
     * Relación con Sucursal:
     * Una cita pertenece a una única sucursal.
     * FetchType.EAGER: La sucursal se cargará junto con la cita, ya que es un dato fundamental.
     */
    // Se eliminó (fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Sucursal sucursal;
}