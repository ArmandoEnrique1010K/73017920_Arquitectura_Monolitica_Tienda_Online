package com.tiendaonline.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDto {

    // carrito
    private Long id_carrito;
    private Long numero;
    private Date fecha_creacion;
    private Date fecha_recibida;
    private Double total_a_pagar;
    private Boolean estado_carrito;
    // usuario
    private Long id_usuario;
    private String usuario_nombre_apellidos;
    private String usuario_email;
    // detalles
    private List<DetallesCarritoDto> detalles_carrito = new ArrayList<>();
} 


    //
    //private Long id_detalles_carrito;

    // usuario
    //private DatosUsuarioDto datosUsuarioDto;
    // detalles
    //private List<DetallesCarritoDto> detallesCarritoDto = new ArrayList<>(); // Inicializa la lista



/*
    CarritoDto carritoDto = CarritoDto.builder()
        .detallesCarritoDto(new ArrayList<>())
        .build();
 */
