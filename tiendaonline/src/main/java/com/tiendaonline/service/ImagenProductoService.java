package com.tiendaonline.service;

import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenProductoService {

    // PREPARA EL CONTENEDOR DE IMAGENES
    public void IniciarContenedorImagenes();
    
    // ALMACENAR UNA IMAGEN ASOCIADA A UN PRODUCTO
    public String AlmacenarUnaImagen(Long id_producto, MultipartFile imagen);

    // CARGAR UNA IMAGEN DEL CONTENEDOR POR SU NOMBRE
    public Path CargarImagen(String nombreImagen);

    // CARGAR UNA IMAGEN COMO RECURSO POR SU NOMBRE
    public Resource CargarComoRecurso(String nombreImagen);
    
    // ELIMINAR DEFINITIVAMENTE UNA IMAGEN DEL CONTENEDOR
    public void EliminarImagen(String nombreImagen);

}
