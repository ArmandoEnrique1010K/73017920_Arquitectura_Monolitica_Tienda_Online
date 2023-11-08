package com.tiendaonline.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DatosUsuarioDto {

    private Long id_usuario;
    
    @NotBlank(message = "El campo no puede estar vacio")
    private String nombre;
    
    @NotBlank(message = "El campo no puede estar vacio")
    private String apellido;
    
    @NotNull(message = "El campo no puede estar vacio")
    private Integer edad;
    
    @NotNull(message = "El campo no puede estar vacio")
    private Integer dni;
    
    @NotBlank(message = "Seleccione una opción")
    private String sexo;
    
    @NotBlank(message = "El campo no puede estar vacio")
    private String email;
    
    @NotBlank(message = "El campo no puede estar vacio")
    private String password;
    
}
