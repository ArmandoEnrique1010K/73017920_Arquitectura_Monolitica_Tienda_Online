package com.tiendaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnProductoDto {
    // DTO PARA UN PRODUCTO QUE SE VA A MOSTRAR AL USUARIO
    // Producto
    private Long id_producto;
    private String codigo;
    private String nombre;
    private Boolean oferta;
    private Double precionormal;
    private Double preciooferta;
    // Categoria
    private String categoria_nombre;
    // Marca
    private String marca_nombre;
    // Imagen producto
    private String imagenproducto_rutaimagen;
    // Caracteristicas producto
    private String caracteristicasproducto_descripcion;
    private String caracteristicasproducto_fichatecnica;
}
