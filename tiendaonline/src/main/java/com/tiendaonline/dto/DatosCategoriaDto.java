package com.tiendaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosCategoriaDto {
    // DTO PARA UNA LISTA DE CATEGORIAS QUE SE VA A MOSTRAR AL ADMINISTRADOR
    private Long id_categoria;
    private String nombre;
    private Boolean estado;
}