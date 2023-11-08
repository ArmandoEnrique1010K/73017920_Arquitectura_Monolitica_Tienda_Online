package com.tiendaonline.service;

import com.tiendaonline.dto.DatosProductoDto;
import com.tiendaonline.dto.ProductoDto;
import com.tiendaonline.dto.UnProductoDto;
import com.tiendaonline.dto.VariosProductosDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

public interface ProductoService {

    // ------------------ METODOS PARA EL ADMINISTRADOR ------------------
    // LISTA DE TODOS LOS PRODUCTOS EN EL SISTEMA QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    public List<DatosProductoDto> ListarProductosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave
    );

    // CONTAR TODOS LOS PRODUCTOS EN EL SISTEMA QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    public Long ContarProductosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave
    );

    // OBTENER UN PRODUCTO CON DETALLES COMPLETOS
    public ProductoDto ObtenerProductoPorId(Long id_producto);

    // GUARDAR UN PRODUCTO
    public ProductoDto GuardarProducto(ProductoDto productoDto, MultipartFile imagen);

    // ACTUALIZAR UN PRODUCTO
    public ProductoDto ActualizarProducto(Long id_producto, ProductoDto productoDto, MultipartFile imagen);

    // INHABILITAR UN PRODUCTO
    public void EliminarProducto(Long id_producto);

    // HABILITAR UN PRODUCTO
    public void RestaurarProducto(Long id_producto);

    // ------------------ METODOS PARA EL USUARIO ------------------
    // LISTA DE PRODUCTOS HABILITADOS QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    public List<VariosProductosDto> ListarProductosHabilitadosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave
    );

    // CANTIDAD DE PRODUCTOS HABILITADOS QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    public Long ContarProductosHabilitadosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave
    );

    // OBTENER UN PRODUCTO POR SU ID
    public UnProductoDto ObtenerProductoPorId2(Long id_producto);

    // PARA EL USUARIO:
    // PAGINA DE PRODUCTOS HABILITADOS QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    public Page<VariosProductosDto> PaginarProductosHabilitadosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave,
            Pageable pageable
    );

    // PARA EL ADMINISTRADOR:
    // PAGINA DE TODOS LOS PRODUCTOS EN EL SISTEMA QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    public Page<DatosProductoDto> PaginarProductosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave,
            Pageable pageable
    );

    
    /*
    public Page<DatosProductoDto> PaginarProductosPorFiltrosDeBusquedaEnOrden(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave,
            String ordenarPor,
            Pageable pageable,
            Sort sort
    );
*/
    
    
}
