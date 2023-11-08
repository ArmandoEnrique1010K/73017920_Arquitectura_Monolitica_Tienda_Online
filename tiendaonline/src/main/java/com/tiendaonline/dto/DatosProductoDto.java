package com.tiendaonline.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosProductoDto {
    // DTO PARA UNA LISTA DE PRODUCTOS QUE SE VA A MOSTRAR AL ADMINISTRADOR
    // Producto
    private Long id_producto;
    private String codigo; 
    private String nombre; 
    private Boolean oferta;
    private Double precionormal; 
    private Double preciooferta; 
    private Boolean estado;
    
    private Date fechaeditado;

    // Categoria
    private String categoria_nombre;
    private Boolean categoria_estado;
    // Marca
    private String marca_nombre;
    private Boolean marca_estado;
    // Imagen producto
    private String imagenproducto_rutaimagen;
}