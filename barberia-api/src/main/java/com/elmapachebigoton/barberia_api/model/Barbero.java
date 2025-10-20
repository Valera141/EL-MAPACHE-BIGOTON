package com.elmapachebigoton.barberia_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // <-- Asegúrate de que esta línea exista
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
    private String fotoUrl;

    // Esta anotación es crucial para evitar el error de serialización del proxy
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;
}