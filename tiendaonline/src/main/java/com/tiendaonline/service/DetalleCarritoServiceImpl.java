package com.tiendaonline.service;

import com.tiendaonline.dto.DetallesCarritoDto;
import com.tiendaonline.entity.DetallesCarritoEntity;
import com.tiendaonline.repository.DetallesCarritoRepository;
import com.tiendaonline.repository.ProductoRepository;
import com.tiendaonline.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleCarritoServiceImpl implements DetalleCarritoService {

    @Autowired
    private DetallesCarritoRepository detallesCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public DetallesCarritoDto guardarDetalleCarrito(DetallesCarritoDto detallesCarritoDto) {
        // Convierte el DTO a una entidad DetallesCarritoEntity si es necesario
        DetallesCarritoEntity detallesCarritoEntity = convertirDtoAEntity(detallesCarritoDto);

        // Guarda la entidad en la base de datos
        detallesCarritoEntity = detallesCarritoRepository.save(detallesCarritoEntity);

        // Convierte la entidad guardada de nuevo a un DTO
        DetallesCarritoDto detallesCarritoGuardadoDto = convertirEntityADto(detallesCarritoEntity);

        return detallesCarritoGuardadoDto;
    }

    // Método para convertir un DetallesCarritoDto a una entidad DetallesCarritoEntity
    private DetallesCarritoEntity convertirDtoAEntity(DetallesCarritoDto detallesCarritoDto) {
        DetallesCarritoEntity detallesCarritoEntity = new DetallesCarritoEntity();
        detallesCarritoEntity.setId_detallecarrito(detallesCarritoDto.getId_detallescarrito());
        detallesCarritoEntity.setCantidad(detallesCarritoDto.getCantidad());
        detallesCarritoEntity.setTotal(detallesCarritoDto.getTotal());
        // Copia otros atributos según sea necesario
        return detallesCarritoEntity;
    }

    // Método para convertir una entidad DetallesCarritoEntity a un DetallesCarritoDto
    private DetallesCarritoDto convertirEntityADto(DetallesCarritoEntity detallesCarritoEntity) {
        DetallesCarritoDto detallesCarritoDto = new DetallesCarritoDto();
        detallesCarritoDto.setId_detallescarrito(detallesCarritoEntity.getId_detallecarrito());
        detallesCarritoDto.setCantidad(detallesCarritoEntity.getCantidad());
        detallesCarritoDto.setTotal(detallesCarritoEntity.getTotal());
        // Copia otros atributos según sea necesario
        return detallesCarritoDto;
    }
}

/*
    @Override
    public DetallesCarritoDto guardarDetalleCarrito(DetallesCarritoDto detallesCarritoDto, CarritoDto carritoDto) {
        if (carritoDto != null) {
            // Convierte el CarritoDto en un CarritoEntity
            //CarritoEntity carritoEntity = convertirCarritoDtoACarritoEntity(carritoDto);
            CarritoEntity carritoEntity = new CarritoEntity();
            carritoEntity.setId_carrito(carritoDto.getId_carrito());
            carritoEntity.setNumero(carritoDto.getNumero());
            carritoEntity.setFecha_creacion(carritoDto.getFecha_creacion());
            carritoEntity.setFecha_recibida(carritoDto.getFecha_recibida());
            carritoEntity.setTotal_a_pagar(carritoDto.getTotal_a_pagar());
            carritoEntity.setEstado_carrito(carritoDto.getEstado_carrito());

            // Configura la relación con el usuario
            UsuarioEntity usuarioEntity = usuarioRepository.findById(carritoDto.getDatosUsuarioDto().getId_usuario()).orElse(null);
            carritoEntity.setUsuarioEntity(usuarioEntity);


            // Crea un nuevo DetallesCarritoEntity y configura las relaciones
            DetallesCarritoEntity detallesCarritoEntity = DetallesCarritoEntity.builder()
                    .cantidad(detallesCarritoDto.getCantidad())
                    .total(detallesCarritoDto.getTotal())
                    .carritoEntity(carritoEntity)
                    .build();

            // Configura la relación con el producto
            ProductoEntity productoEntity = productoRepository.findById(detallesCarritoDto.getProductodto().getId_producto()).orElse(null);
            detallesCarritoEntity.setProductoEntity(productoEntity);

            // Guardar el detalle del carrito en la base de datos
            detallesCarritoEntity = detallesCarritoRepository.save(detallesCarritoEntity);

            // Establecer el ID del detalle en el DTO
            detallesCarritoDto.setId_detallescarrito(detallesCarritoEntity.getId_detallecarrito());

            return detallesCarritoDto;
        } else {
            return null;
        }
 */
 /*
        if (carritoDto != null) {
            // Convierte el CarritoDto en un CarritoEntity
            CarritoEntity carritoEntity = convertirCarritoDtoACarritoEntity(carritoDto);

            // Crea un nuevo DetallesCarritoEntity y configura las relaciones
            DetallesCarritoEntity detallesCarritoEntity = DetallesCarritoEntity.builder()
                    .cantidad(detallesCarritoDto.getCantidad())
                    .total(detallesCarritoDto.getTotal())
                    .carritoEntity(carritoEntity)
                    .build();

            // Configura la relación con el producto
            ProductoEntity productoEntity = productoRepository.findById(detallesCarritoDto.getProductodto().getId_producto()).orElse(null);
            detallesCarritoEntity.setProductoEntity(productoEntity);

            // Guardar el detalle del carrito en la base de datos
            detallesCarritoEntity = detallesCarritoRepository.save(detallesCarritoEntity);

            // Establecer el ID del detalle en el DTO
            detallesCarritoDto.setId_detallescarrito(detallesCarritoEntity.getId_detallecarrito());

            return detallesCarritoDto;
        } else {
            return null;
        }
 */
 /*
    private CarritoEntity convertirCarritoDtoACarritoEntity(CarritoDto carritoDto) {
        CarritoEntity carritoEntity = new CarritoEntity();
        carritoEntity.setId_carrito(carritoDto.getId_carrito());
        carritoEntity.setNumero(carritoDto.getNumero());
        carritoEntity.setFecha_creacion(carritoDto.getFecha_creacion());
        carritoEntity.setFecha_recibida(carritoDto.getFecha_recibida());
        carritoEntity.setTotal_a_pagar(carritoDto.getTotal_a_pagar());
        carritoEntity.setEstado_carrito(carritoDto.getEstado_carrito());

        // Configura la relación con el usuario
        UsuarioEntity usuarioEntity = usuarioRepository.findById(carritoDto.getDatosUsuarioDto().getId_usuario()).orElse(null);
        carritoEntity.setUsuarioEntity(usuarioEntity);

        return carritoEntity;
    }
 */
 /*
            DetallesCarritoEntity detallesCarritoEntity = new DetallesCarritoEntity();
            detallesCarritoEntity.setCantidad(detallesCarritoDto.getCantidad());
            detallesCarritoEntity.setTotal(detallesCarritoDto.getTotal());
            detallesCarritoEntity.setCarrito(carritoEntity); // Asigna el carrito al detalle
            
 */
