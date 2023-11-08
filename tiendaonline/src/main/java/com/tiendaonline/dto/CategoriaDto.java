package com.tiendaonline.dto;

import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {
    // DTO PARA UNA LISTA DE CATEGORIAS Y UNA CATEGORIA QUE SE VA A MOSTRAR AL USUARIO Y AL ADMINISTRADOR (PARA EL METODO CREAR Y ACTUALIZAR)
    private Long id_categoria;
    @NotBlank (message = "El campo no puede estar vacio")
    private String nombre;
    

}
