package com.tiendaonline.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarcaDto {
    // DTO PARA UNA LISTA DE MARCAS Y UNA MARCA QUE SE VA A MOSTRAR AL USUARIO Y AL ADMINISTRADOR (PARA EL METODO CREAR Y ACTUALIZAR)
    private Long id_marca;
    @NotBlank (message = "El campo no puede estar vacio")
    private String nombre;
}

