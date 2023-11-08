package com.tiendaonline.service;

import com.tiendaonline.dto.CategoriaDto;
import com.tiendaonline.dto.DatosCategoriaDto;
import java.util.List;

// La interfaz CategoriaService define métodos para operaciones relacionadas con categorías en una aplicación de comercio en línea.
public interface CategoriaService {
    
    // ------------------ METODOS PARA EL ADMINISTRADOR ------------------
    
    // LISTA DE CATEGORIAS
    public List<DatosCategoriaDto> ListarCategorias();  
    
    // OBTENER UNA CATEGORIA POR SU ID
    public CategoriaDto ObtenerCategoriaPorId(Long id_categoria);
    
    // GUARDAR UNA NUEVA CATEGORIA
    public CategoriaDto GuardarCategoria(CategoriaDto categoriaDto);

    // EDITAR UNA CATEGORIA (CAMBIAR DE NOMBRE)
    public CategoriaDto CambiarNombreCategoria(Long id_categoria, CategoriaDto categoriaDto);
    
    // INHABILITAR UNA CATEGORIA
    public void EliminarCategoria(Long id_categoria);

    // HABILITAR UNA CATEGORIA
    public void RestaurarCategoria(Long id_categoria);
    

    
    // ------------------ METODOS PARA EL USUARIO ------------------
    
    // LISTA DE CATEGORIAS CUYO ESTADO SEA TRUE
    public List<CategoriaDto> ListarCategoriasByEstadoTrue();

    // OBTENER UNA CATEGORIA POR SU NOMBRE
    public CategoriaDto ObtenerCategoriaPorNombre(String nombreCategoria);
    
}
