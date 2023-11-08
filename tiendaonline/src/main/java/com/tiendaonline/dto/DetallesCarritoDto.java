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

public class DetallesCarritoDto {
    private Long id_detallescarrito;
    private Integer cantidad;
    private Double total;
    
    private Long id_producto;
    private String nombre_producto;
    private Double precio_producto;
    
    private Long id_carrito;
}

    // private ProductoDto productodto;

    //private String producto_nombre;
    //private Double producto_precio_normal;
    //private Double producto_precio_oferta;


    // carrito
    //private Long id_carrito;
    // productos
    //private Long id_producto;
    //
    // private UnProductoDto unProductoDto;
