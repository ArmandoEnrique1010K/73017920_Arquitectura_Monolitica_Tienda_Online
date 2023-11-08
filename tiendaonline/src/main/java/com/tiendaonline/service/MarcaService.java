package com.tiendaonline.service;

import com.tiendaonline.dto.DatosMarcaDto;
import com.tiendaonline.dto.MarcaDto;
import java.util.List;

public interface MarcaService {

    // ------------------ METODOS PARA EL ADMINISTRADOR ------------------
    
    // LISTA DE MARCAS
    public List<DatosMarcaDto> ListarMarcas();  

    // OBTENER UNA MARCA POR SU ID
    public MarcaDto ObtenerMarcaPorId(Long id_marca);
    
    // GUARDAR UNA NUEVA MARCA
    public MarcaDto GuardarMarca(MarcaDto marcaDto);

    // EDITAR UNA MARCA (CAMBIAR DE NOMBRE)
    public MarcaDto CambiarNombreMarca(Long id_marca, MarcaDto marcaDto);

    // INHABILITAR UNA MARCA
    public void EliminarMarca(Long id_marca);

    // HABILITAR UNA MARCA
    public void RestaurarMarca(Long id_marca);

    
    
    // ------------------ METODOS PARA EL USUARIO ------------------
    
    // LISTA DE MARCAS CUYO ESTADO SEA TRUE
    public List<MarcaDto> ListarMarcasByEstadoTrue();

    // LISTA DE MARCAS DISTINTAS QUE PERTENEZCAN A LA MISMA CATEGORIA POR EL NOMBRE DE LA CATEGORIA
    public List<MarcaDto> ListarDistintasMarcasByGrupoCategoriaAndEstadoTrue(String nombreCategoria);

}
