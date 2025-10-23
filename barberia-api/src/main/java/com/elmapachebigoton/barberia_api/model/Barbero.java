package com.elmapachebigoton.barberia_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // 1. Importa esta clase
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

    @Column(name = "foto_url", nullable = false)
    private String foto_url;

    // 2. Esta anotación soluciona el error de serialización
    @JsonIgnoreProperties({"hibernateEagerInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;
}