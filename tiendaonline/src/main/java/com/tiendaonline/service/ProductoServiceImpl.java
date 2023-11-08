package com.tiendaonline.service;

import com.tiendaonline.dto.DatosProductoDto;
import com.tiendaonline.dto.ProductoDto;
import com.tiendaonline.dto.UnProductoDto;
import com.tiendaonline.dto.VariosProductosDto;
import com.tiendaonline.entity.CaracteristicasProductoEntity;
import com.tiendaonline.entity.CategoriaEntity;
import com.tiendaonline.entity.ImagenProductoEntity;
import com.tiendaonline.entity.MarcaEntity;
import com.tiendaonline.entity.ProductoEntity;
import com.tiendaonline.exception.ObjetoNoEncontradoException;
import com.tiendaonline.repository.CaracteristicasProductoRepository;
import com.tiendaonline.repository.CategoriaRepository;
import com.tiendaonline.repository.ImagenProductoRepository;
import com.tiendaonline.repository.MarcaRepository;
import com.tiendaonline.repository.ProductoRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CaracteristicasProductoRepository caracteristicasProductoRepository;

    @Autowired
    private ImagenProductoRepository imagenProductoRepository;

    @Autowired
    private ImagenProductoService imagenProductoService;

    // LISTA DE TODOS LOS PRODUCTOS EN EL SISTEMA QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    @Override
    public List<DatosProductoDto> ListarProductosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave) {

        List<ProductoEntity> listProductoEntity = productoRepository.findAllByParams(
                categoriaId, marcaIds, minPrecio, maxPrecio, enOferta, palabraClave);
        List<DatosProductoDto> list = new ArrayList<>();

        for (ProductoEntity productoEntity : listProductoEntity) {
            DatosProductoDto productoDto = DatosProductoDto.builder()
                    .id_producto(productoEntity.getId_producto())
                    .codigo(productoEntity.getCodigo())
                    .nombre(productoEntity.getNombre())
                    .oferta(productoEntity.getOferta())
                    .precionormal(productoEntity.getPrecionormal())
                    .preciooferta(productoEntity.getPreciooferta())
                    .estado(productoEntity.getEstado())
                    .categoria_nombre(productoEntity.getCategoriaEntity().getNombre())
                    .categoria_estado(productoEntity.getCategoriaEntity().getEstado())
                    .marca_nombre(productoEntity.getMarcaEntity().getNombre())
                    .marca_estado(productoEntity.getMarcaEntity().getEstado())
                    .imagenproducto_rutaimagen(productoEntity.getImagenProductoEntity().getRutaimagen())
                    .build();
            list.add(productoDto);
        }

        return list;

    }

    // CONTAR TODOS LOS PRODUCTOS EN EL SISTEMA QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    @Override
    public Long ContarProductosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave) {
        return productoRepository.countAllByParams(
                categoriaId, marcaIds, minPrecio, maxPrecio, enOferta, palabraClave);
    }

    // OBTENER UN PRODUCTO CON DETALLES COMPLETOS
    @Override
    public ProductoDto ObtenerProductoPorId(Long id_producto) {

        ProductoEntity productoEntity = productoRepository.findById(id_producto).orElse(null);

        if (productoEntity == null) {
            throw new ObjetoNoEncontradoException("El producto no existe.");
        }

        ProductoDto productoDto = ProductoDto.builder()
                .id_producto(productoEntity.getId_producto())
                .codigo(productoEntity.getCodigo())
                .nombre(productoEntity.getNombre())
                .oferta(productoEntity.getOferta())
                .precionormal(productoEntity.getPrecionormal())
                .preciooferta(productoEntity.getPreciooferta())
                .estado(productoEntity.getEstado())
                .fechaeditado(productoEntity.getFechaeditado())
                .categoria_id_categoria(productoEntity.getCategoriaEntity().getId_categoria())
                .categoria_nombre(productoEntity.getCategoriaEntity().getNombre())
                .marca_id_marca(productoEntity.getMarcaEntity().getId_marca())
                .marca_nombre(productoEntity.getMarcaEntity().getNombre())
                .imagenproducto_rutaimagen(productoEntity.getImagenProductoEntity().getRutaimagen())
                .caracteristicasproducto_descripcion(productoEntity.getCaracteristicasProductoEntity().getDescripcion())
                .caracteristicasproducto_fichatecnica(productoEntity.getCaracteristicasProductoEntity().getFichatecnica())
                .build();

        return productoDto;

    }

    // GUARDAR UN PRODUCTO
    @Override
    public ProductoDto GuardarProducto(ProductoDto productoDto, MultipartFile imagen) {

        // 1° VERIFICAR SI LA CATEGORIA Y LA MARCA EXISTE EN LA BASE DE DATOS
        CategoriaEntity categoria = categoriaRepository.findById(productoDto.getCategoria_id_categoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        MarcaEntity marca = marcaRepository.findById(productoDto.getMarca_id_marca())
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));

        // 2° CREAR UNA INSTANCIA DE CaracteristicasProductoEntity
        CaracteristicasProductoEntity caracteristicasProducto = CaracteristicasProductoEntity.builder()
                .descripcion(productoDto.getCaracteristicasproducto_descripcion())
                .fichatecnica(productoDto.getCaracteristicasproducto_fichatecnica())
                .build();

        // Guarda los detalles de CaracteristicasProductoEntity en la base de datos
        caracteristicasProducto = caracteristicasProductoRepository.save(caracteristicasProducto);

        // 3° GUARDAR LA IMAGEN Y OBTENER EL NOMBRE ÚNICO
        String nuevoNombre = imagenProductoService.AlmacenarUnaImagen(productoDto.getId_producto(), imagen);

        // Crea una nueva instancia de ImagenProductoEntity
        ImagenProductoEntity imagenProducto = new ImagenProductoEntity();
        imagenProducto.setRutaimagen(nuevoNombre);

        // Guarda la imagen en la base de datos
        imagenProducto = imagenProductoRepository.save(imagenProducto);

        Date today = new Date();

        // 4° CREAR UNA INSTANCIA DE ProductoEntity
        ProductoEntity productoEntity = ProductoEntity.builder()
                .codigo(productoDto.getCodigo())
                .nombre(productoDto.getNombre())
                .oferta(productoDto.getOferta())
                .precionormal(productoDto.getPrecionormal())
                .preciooferta(productoDto.getPreciooferta())
                .estado(Boolean.TRUE)
                .categoriaEntity(categoria)
                .marcaEntity(marca)
                .caracteristicasProductoEntity(caracteristicasProducto)
                .fechaeditado(today)
                .build();

        // Asigna la imagen a productoEntity
        productoEntity.setImagenProductoEntity(imagenProducto);

        // Guarda el producto en la base de datos
        productoEntity = productoRepository.save(productoEntity);
        productoDto.setId_producto(productoEntity.getId_producto());

        return productoDto;
    }

    // ACTUALIZAR UN PRODUCTO 
    @Override
    public ProductoDto ActualizarProducto(Long id_producto, ProductoDto productoDto, MultipartFile imagen) {

        // Busca y valida el producto en la base de datos
        Optional<ProductoEntity> productoOptional = productoRepository.findById(id_producto);

        if (productoOptional.isPresent()) {
            ProductoEntity productoEntity = productoOptional.get();

            Date today = new Date();

            // Actualiza los detalles del producto con los valores proporcionados
            productoEntity.setCodigo(productoDto.getCodigo());
            productoEntity.setNombre(productoDto.getNombre());
            productoEntity.setOferta(productoDto.getOferta());
            productoEntity.setPrecionormal(productoDto.getPrecionormal());
            productoEntity.setPreciooferta(productoDto.getPreciooferta());
            productoEntity.setFechaeditado(today);

            // Busca y valida la categoría en la base de datos
            CategoriaEntity categoria = categoriaRepository.findById(productoDto.getCategoria_id_categoria())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
            productoEntity.setCategoriaEntity(categoria);

            // Busca y valida la marca en la base de datos
            MarcaEntity marca = marcaRepository.findById(productoDto.getMarca_id_marca())
                    .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
            productoEntity.setMarcaEntity(marca);

            // Obtiene y actualiza los detalles de CaracteristicasProductoEntity asociados
            CaracteristicasProductoEntity caracteristicasProducto = productoEntity.getCaracteristicasProductoEntity();
            caracteristicasProducto.setDescripcion(productoDto.getCaracteristicasproducto_descripcion());
            caracteristicasProducto.setFichatecnica(productoDto.getCaracteristicasproducto_fichatecnica());
            caracteristicasProducto = caracteristicasProductoRepository.save(caracteristicasProducto);
            productoEntity.setCaracteristicasProductoEntity(caracteristicasProducto);

            // SOLAMENTE SI SE HA SUBIDO UNA IMAGEN
            if (!imagen.isEmpty()) {

                if (productoEntity.getImagenProductoEntity() != null) {
                    // Elimina la imagen anterior
                    imagenProductoService.EliminarImagen(productoEntity.getImagenProductoEntity().getRutaimagen());
                }

                // Guarda la nueva imagen y obtiene su nombre único
                String nuevoNombre = imagenProductoService.AlmacenarUnaImagen(productoDto.getId_producto(), imagen);
                // Actualiza la ruta de la imagen en la entidad de imagen existente
                productoEntity.getImagenProductoEntity().setRutaimagen(nuevoNombre);
                // Asegúrate de que se actualice en la base de datos
                imagenProductoRepository.save(productoEntity.getImagenProductoEntity());

            }

            // Guarda los cambios del producto en la base de datos
            productoRepository.save(productoEntity);

            return productoDto;

        } else {

            throw new ObjetoNoEncontradoException("El producto con el ID " + id_producto + " no existe.");

        }
    }

    // INHABILITAR UN PRODUCTO SIN INCLUIR LA IMAGEN
    @Override
    public void EliminarProducto(Long id_producto) {

        ProductoEntity productoEntity = productoRepository.findById(id_producto).orElse(null);

        if (productoEntity != null) {

            productoEntity.setEstado(Boolean.FALSE);
            productoRepository.save(productoEntity);

        } else {

            throw new ObjetoNoEncontradoException("El producto con el ID " + id_producto + " no existe.");

        }

    }

    // HABILITAR UN PRODUCTO SIN INCLUIR LA IMAGEN
    @Override
    public void RestaurarProducto(Long id_producto) {

        ProductoEntity productoEntity = productoRepository.findById(id_producto).orElse(null);

        if (productoEntity != null) {

            // Establece el estado del producto como activo
            productoEntity.setEstado(Boolean.TRUE);
            productoRepository.save(productoEntity);

        } else {

            throw new ObjetoNoEncontradoException("El producto con el ID " + id_producto + " no existe.");

        }
    }

    // LISTA DE PRODUCTOS HABILITADOS QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    @Override
    public List<VariosProductosDto> ListarProductosHabilitadosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave) {

        List<ProductoEntity> listProductoEntity = productoRepository.findAllByEstadoTrueAndParams(
                categoriaId, marcaIds, minPrecio, maxPrecio, enOferta, palabraClave);
        List<VariosProductosDto> list = new ArrayList<>();

        for (ProductoEntity productoEntity : listProductoEntity) {
            VariosProductosDto variosProductosDto = VariosProductosDto.builder()
                    .id_producto(productoEntity.getId_producto())
                    .codigo(productoEntity.getCodigo())
                    .nombre(productoEntity.getNombre())
                    .oferta(productoEntity.getOferta())
                    .precionormal(productoEntity.getPrecionormal())
                    .preciooferta(productoEntity.getPreciooferta())
                    .categoria_nombre(productoEntity.getCategoriaEntity().getNombre())
                    .marca_nombre(productoEntity.getMarcaEntity().getNombre())
                    .imagenproducto_rutaimagen(productoEntity.getImagenProductoEntity().getRutaimagen())
                    .build();
            list.add(variosProductosDto);
        }

        return list;

    }

    // CANTIDAD DE PRODUCTOS HABILITADOS QUE COINCIDEN CON LOS FILTROS DE BUSQUEDA
    @Override
    public Long ContarProductosHabilitadosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave) {

        return productoRepository.countAllByEstadoTrueAndParams(
                categoriaId, marcaIds, minPrecio, maxPrecio, enOferta, palabraClave);
    }

    // OBTENER UN PRODUCTO POR SU ID
    @Override
    public UnProductoDto ObtenerProductoPorId2(Long id_producto) {

        ProductoEntity productoEntity = productoRepository.findById(id_producto).orElse(null);

        if (productoEntity == null) {
            throw new ObjetoNoEncontradoException("El producto no existe.");
        }

        // Si el estado del producto es falso, entonces va a mostrar un error
        if (productoEntity.getEstado() == false) {
            throw new ObjetoNoEncontradoException("El producto no existe.");
        }

        UnProductoDto unProductoDto = UnProductoDto.builder()
                .id_producto(productoEntity.getId_producto())
                .codigo(productoEntity.getCodigo())
                .nombre(productoEntity.getNombre())
                .oferta(productoEntity.getOferta())
                .precionormal(productoEntity.getPrecionormal())
                .preciooferta(productoEntity.getPreciooferta())
                .categoria_nombre(productoEntity.getCategoriaEntity().getNombre())
                .marca_nombre(productoEntity.getMarcaEntity().getNombre())
                .imagenproducto_rutaimagen(productoEntity.getImagenProductoEntity().getRutaimagen())
                .caracteristicasproducto_descripcion(productoEntity.getCaracteristicasProductoEntity().getDescripcion())
                .caracteristicasproducto_fichatecnica(productoEntity.getCaracteristicasProductoEntity().getFichatecnica())
                .build();

        return unProductoDto;

    }

    // Realiza una búsqueda paginada de productos habilitados que cumplen con los criterios de búsqueda.
    @Override
    public Page<VariosProductosDto> PaginarProductosHabilitadosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave,
            Pageable pageable
    ) {

        // Realiza la consulta en la base de datos
        Page<ProductoEntity> pageProductoEntity = productoRepository.pageAllByEstadoTrueAndParams(
                categoriaId, marcaIds, minPrecio, maxPrecio, enOferta, palabraClave, pageable
        );

        // Inicializa una lista para almacenar los resultados transformados en DTOs
        List<VariosProductosDto> list = new ArrayList<>();

        // Transforma los resultados de la entidad ProductoEntity en DTOs VariosProductosDto
        for (ProductoEntity productoEntity : pageProductoEntity) {
            VariosProductosDto variosProductosDto = VariosProductosDto.builder()
                    .id_producto(productoEntity.getId_producto())
                    .codigo(productoEntity.getCodigo())
                    .nombre(productoEntity.getNombre())
                    .oferta(productoEntity.getOferta())
                    .precionormal(productoEntity.getPrecionormal())
                    .preciooferta(productoEntity.getPreciooferta())
                    .categoria_nombre(productoEntity.getCategoriaEntity().getNombre())
                    .marca_nombre(productoEntity.getMarcaEntity().getNombre())
                    .imagenproducto_rutaimagen(productoEntity.getImagenProductoEntity().getRutaimagen())
                    .build();
            list.add(variosProductosDto);
        }

        // Devuelve una página paginada de resultados con los DTOs
        return new PageImpl<>(list, pageable, pageProductoEntity.getTotalElements());

    }

    @Override
    public Page<DatosProductoDto> PaginarProductosPorFiltrosDeBusqueda(
            Long categoriaId,
            List<Long> marcaIds,
            Double minPrecio,
            Double maxPrecio,
            Boolean enOferta,
            String palabraClave,
            Pageable pageable
    ) {

        Page<ProductoEntity> pageProductoEntity = productoRepository.pageAllByParams(categoriaId, marcaIds, minPrecio, maxPrecio, enOferta, palabraClave, pageable);
        List<DatosProductoDto> list = new ArrayList<>();

        for (ProductoEntity productoEntity : pageProductoEntity) {
            DatosProductoDto productoDto = DatosProductoDto.builder()
                    .id_producto(productoEntity.getId_producto())
                    .codigo(productoEntity.getCodigo())
                    .nombre(productoEntity.getNombre())
                    .oferta(productoEntity.getOferta())
                    .precionormal(productoEntity.getPrecionormal())
                    .preciooferta(productoEntity.getPreciooferta())
                    .estado(productoEntity.getEstado())
                    .fechaeditado(productoEntity.getFechaeditado())
                    .categoria_nombre(productoEntity.getCategoriaEntity().getNombre())
                    .categoria_estado(productoEntity.getCategoriaEntity().getEstado())
                    .marca_nombre(productoEntity.getMarcaEntity().getNombre())
                    .marca_estado(productoEntity.getMarcaEntity().getEstado())
                    .imagenproducto_rutaimagen(productoEntity.getImagenProductoEntity().getRutaimagen())
                    .build();
            list.add(productoDto);
        }

        return new PageImpl<>(list, pageable, pageProductoEntity.getTotalElements());
    }

    /*
    @Override
    public Page<DatosProductoDto> PaginarProductosPorFiltrosDeBusquedaEnOrden(Long categoriaId, List<Long> marcaIds, Double minPrecio, Double maxPrecio, Boolean enOferta, String palabraClave, String ordenarPor, Pageable pageable, Sort sort) {

        Page<ProductoEntity> pageProductoEntity = productoRepository.pageAndOrderAllByParams(categoriaId, marcaIds, minPrecio, maxPrecio, enOferta, palabraClave, ordenarPor, pageable);
        List<DatosProductoDto> list = new ArrayList<>();

        for (ProductoEntity productoEntity : pageProductoEntity) {
            DatosProductoDto productoDto = DatosProductoDto.builder()
                    .id_producto(productoEntity.getId_producto())
                    .codigo(productoEntity.getCodigo())
                    .nombre(productoEntity.getNombre())
                    .oferta(productoEntity.getOferta())
                    .precionormal(productoEntity.getPrecionormal())
                    .preciooferta(productoEntity.getPreciooferta())
                    .estado(productoEntity.getEstado())
                    .fechaeditado(productoEntity.getFechaeditado())
                    .categoria_nombre(productoEntity.getCategoriaEntity().getNombre())
                    .categoria_estado(productoEntity.getCategoriaEntity().getEstado())
                    .marca_nombre(productoEntity.getMarcaEntity().getNombre())
                    .marca_estado(productoEntity.getMarcaEntity().getEstado())
                    .imagenproducto_rutaimagen(productoEntity.getImagenProductoEntity().getRutaimagen())
                    .build();
            list.add(productoDto);
        }

        return new PageImpl<>(list, pageable, pageProductoEntity.getTotalElements());

    }
*/
}
