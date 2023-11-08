package com.tiendaonline.dto;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {

    // DTO PARA UN PRODUCTO QUE SE VA A MOSTRAR AL ADMINISTRADOR (PARA LOS METODOS ENCONTRAR, CREAR Y ACTUALIZAR)
    private Long id_producto;
    @NotBlank(message = "El campo no puede estar vacio")
    private String codigo;
    @NotBlank(message = "El campo no puede estar vacio")
    private String nombre;
    private Boolean oferta;
    @Min(value = 1, message = "Valor minimo aceptado: 1")
    @NotNull(message = "El campo no puede estar vacio")
    private Double precionormal;
    private Double preciooferta;
    private Boolean estado;
    
    private Date fechaeditado;
    
    // categoria
    @NotNull(message = "Seleccione una categoria")
    private Long categoria_id_categoria;
    private String categoria_nombre;
    // marca
    @NotNull(message = "Seleccione una marca")
    private Long marca_id_marca;
    private String marca_nombre;
    // caracteristicas producto
    private Long caracteristicasproducto_id_caracteristicasproducto;
    @NotBlank(message = "El campo no puede estar vacio")
    private String caracteristicasproducto_descripcion;
    @NotBlank(message = "El campo no puede estar vacio")
    private String caracteristicasproducto_fichatecnica;
    // imagen producto
    private Long imagenproducto_id_imagenproducto;
    private String imagenproducto_rutaimagen;
    private MultipartFile imagenproducto_imagen;
}


/*
    private String categoria_nombre;
    private String marca_nombre;
    private String imagenproducto_rutaimagen;
    private MultipartFile imagenproducto_imagen;
 */
//    private CaracteristicasProductoEntity caracteristicasProductoEntity;
//    private ImagenProductoEntity imagenProductoEntity;

/*
    private CategoriaEntity categoriaEntity;
    private MarcaEntity marcaEntity;
    private CaracteristicasProductoEntity caracteristicasProductoEntity;
    private ImagenProductoEntity imagenProductoEntity;
    private Long categoria_id_categoria;
    private String categoria_nombre;
    private Long marca_id_marca;
    private String marca_nombre;
    private String caracteristicasproducto_descripcion;
    private String caracteristicasproducto_fichatecnica;
    private String imagenproducto_rutaimagen;
    private MultipartFile imagenproducto_imagen;

    private Long categoria_id_categoria;
    private String categoria_nombre;
    private Long marca_id_marca;
    private String marca_nombre;
    private String caracteristicasproducto_descripcion;
    private String caracteristicasproducto_fichatecnica;
    private String imagenproducto_rutaimagen;
    private MultipartFile imagenproducto_imagen;
 */
//@NotNull @NotBlank(message = "Este campo no puede estar en blanco")
//@NotNull @NotBlank(message = "Este campo no puede estar en blanco")

/*

    @NotNull(message = "Este campo no puede estar en blanco")
    private CategoriaEntity categoriaEntity;
    @NotNull(message = "Este campo no puede estar en blanco")
    private MarcaEntity marcaEntity;
    // Caracteristicas producto

    //@NotNull(message = "Este campo no puede estar en blanco")
    private ImagenProductoEntity imagenProductoEntity;


 */
// Imagen producto

