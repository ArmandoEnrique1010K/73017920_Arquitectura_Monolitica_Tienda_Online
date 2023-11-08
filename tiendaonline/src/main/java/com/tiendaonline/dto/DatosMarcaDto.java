package com.tiendaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosMarcaDto {
    // DTO PARA UNA LISTA DE MARCAS QUE SE VA A MOSTRAR AL ADMINISTRADOR
    private Long id_marca;
    private String nombre;
    private Boolean estado;
}
