package com.elmapachebigoton.barberia_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaRequestDTO {

    // El frontend envía 'fecha_hora', Jackson lo mapeará a este campo
    @JsonProperty("fecha_hora") 
    private LocalDateTime fechaHora;

    private IdDTO cliente;
    private IdDTO barbero;
    private IdDTO servicio;
    private IdDTO sucursal;
}