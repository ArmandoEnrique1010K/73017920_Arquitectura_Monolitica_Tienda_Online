package com.tiendaonline.service;

import com.tiendaonline.dto.CarritoDto;
import com.tiendaonline.dto.DatosUsuarioDto;
import com.tiendaonline.entity.CarritoEntity;
import com.tiendaonline.entity.DetallesCarritoEntity;
import com.tiendaonline.entity.UsuarioEntity;
import com.tiendaonline.repository.CarritoRepository;
import com.tiendaonline.repository.UsuarioRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements CarritoService {

    private static final Logger logger = LoggerFactory.getLogger(CarritoServiceImpl.class);

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DetalleCarritoService detalleCarritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public CarritoDto guardarCarrito(CarritoDto carritoDto, DatosUsuarioDto datosUsuarioDto) {

        // Verifica si el usuario ya tiene un carrito asociado
        CarritoDto carritoExistente = obtenerCarritoPorUsuario(datosUsuarioDto);

        if (carritoExistente == null) {
            // Si no se encontró un carrito existente, crea uno nuevo
            carritoExistente = new CarritoDto();
            carritoExistente.setFecha_creacion(new Date());
            carritoExistente.setId_usuario(datosUsuarioDto.getId_usuario());
            carritoExistente.setEstado_carrito(true); // Asigna el estado a "true"
        }

        // Actualiza los campos necesarios en el carrito
        carritoExistente.setTotal_a_pagar(carritoDto.getTotal_a_pagar());

        // Guarda el carrito (ya sea un nuevo carrito o uno existente) en la base de datos
        CarritoEntity carritoEntity = convertirDtoACarritoEntity(carritoExistente);
        carritoEntity = carritoRepository.save(carritoEntity);

        // Convierte la entidad CarritoEntity nuevamente a un CarritoDto
        CarritoDto carritoGuardadoDto = convertirCarritoEntityADto(carritoEntity);

        return carritoGuardadoDto;

        /*
        // Obtén el carrito existente del usuario
        CarritoDto carritoExistente = obtenerCarritoPorUsuario(datosUsuarioDto);
        logger.info("CARRITO DEL USUARIO: " + datosUsuarioDto);

        
        // Actualiza los campos necesarios en el carrito
        carritoExistente.setTotal_a_pagar(carritoDto.getTotal_a_pagar()); // Actualiza el total
        
        carritoExistente.setId_usuario(datosUsuarioDto.getId_usuario());
        logger.info("ID DEL USUARIO: " + datosUsuarioDto.getId_usuario());

        
        // Guarda el carrito actualizado en la base de datos
        CarritoEntity carritoEntity = convertirDtoACarritoEntity(carritoExistente);
        carritoEntity = carritoRepository.save(carritoEntity);

        // Convierte la entidad CarritoEntity nuevamente a un CarritoDto
        CarritoDto carritoGuardadoDto = convertirCarritoEntityADto(carritoEntity);

        return carritoGuardadoDto;
         */
    }

    
    @Override
    public CarritoDto obtenerCarritoPorUsuario(DatosUsuarioDto datosUsuarioDto) {
        // Convierte el DatosUsuarioDto a una entidad UsuarioEntity
        UsuarioEntity usuarioEntity = convertirDatosUsuarioDtoAUsuarioEntity(datosUsuarioDto);

        // Busca el carrito asociado al usuario con estado activo
        List<CarritoEntity> carritoEntityList = carritoRepository.findByUsuarioEntityAndEstadoCarritoTrue(usuarioEntity);

        if (!carritoEntityList.isEmpty()) {
            // Si se encuentra un carrito existente, conviértelo a un CarritoDto
            CarritoEntity carritoEntity = carritoEntityList.get(0); // Obtener el primer carrito
            return convertirCarritoEntityADto(carritoEntity);
        } else {
            // Si no se encuentra un carrito existente, crea uno nuevo
            CarritoDto carritoDto = new CarritoDto();
            carritoDto.setFecha_creacion(new Date());
            carritoDto.setId_usuario(datosUsuarioDto.getId_usuario());
            carritoDto.setEstado_carrito(true); // Asigna el estado a "true"

            // No es necesario guardar el carrito nuevo en este método
            return carritoDto;
        }
    }
/*
        // Convierte el DatosUsuarioDto a una entidad UsuarioEntity
        UsuarioEntity usuarioEntity = convertirDatosUsuarioDtoAUsuarioEntity(datosUsuarioDto);

        // Busca el carrito asociado al usuario con estado activo
        List<CarritoEntity> carritoEntityList = carritoRepository.findByUsuarioEntityAndEstadoCarritoTrue(usuarioEntity);

        if (!carritoEntityList.isEmpty()) {
            // Si se encuentra un carrito existente, conviértelo a un CarritoDto
            CarritoEntity carritoEntity = carritoEntityList.get(0); // Obtener el primer carrito
            return convertirCarritoEntityADto(carritoEntity);
        } else {
            // Si no se encuentra un carrito existente, crea uno nuevo
            CarritoDto carritoDto = new CarritoDto();
            carritoDto.setFecha_creacion(new Date());
            carritoDto.setId_usuario(datosUsuarioDto.getId_usuario());
            carritoDto.setEstado_carrito(true); // Asigna el estado a "true"

            // Guarda el carrito nuevo en la base de datos
            carritoDto = guardarCarrito(carritoDto, datosUsuarioDto);

            return carritoDto;
        }
    }
*/
    /*
     */
    @Override
    public void CalcularTotalCarrito(CarritoDto carritoDto) {
        // Convierte el CarritoDto a una entidad CarritoEntity
        CarritoEntity carritoEntity = convertirDtoACarritoEntity(carritoDto);

        // Realiza el cálculo del total
        double totalCarrito = 0.0;
        for (DetallesCarritoEntity detalleEntity : carritoEntity.getDetallesCarritoEntity()) {
            double precio = detalleEntity.getProductoEntity().getPreciooferta() != null
                    ? detalleEntity.getProductoEntity().getPreciooferta()
                    : detalleEntity.getProductoEntity().getPrecionormal();
            totalCarrito += detalleEntity.getCantidad() * precio;
        }

        // Actualiza el total en la entidad CarritoEntity
        carritoEntity.setTotal_a_pagar(totalCarrito);

        // Guarda la entidad actualizada en la base de datos
        carritoRepository.save(carritoEntity);
    }

    // Método para convertir un CarritoDto a una entidad CarritoEntity
    private CarritoEntity convertirDtoACarritoEntity(CarritoDto carritoDto) {
        CarritoEntity carritoEntity = new CarritoEntity();
        // Copia los atributos del DTO a la entidad
        carritoEntity.setId_carrito(carritoDto.getId_carrito());
        carritoEntity.setNumero(carritoDto.getNumero());
        carritoEntity.setFecha_creacion(carritoDto.getFecha_creacion());
        carritoEntity.setFecha_recibida(carritoDto.getFecha_recibida());
        carritoEntity.setTotal_a_pagar(carritoDto.getTotal_a_pagar());
        carritoEntity.setEstado_carrito(carritoDto.getEstado_carrito());
        // Puedes seguir copiando otros atributos relacionados al usuario y detalles del carrito
        return carritoEntity;
    }

// Método para convertir una entidad CarritoEntity a un CarritoDto
    private CarritoDto convertirCarritoEntityADto(CarritoEntity carritoEntity) {
        CarritoDto carritoDto = new CarritoDto();
        // Copia los atributos de la entidad al DTO
        carritoDto.setId_carrito(carritoEntity.getId_carrito());
        carritoDto.setNumero(carritoEntity.getNumero());
        carritoDto.setFecha_creacion(carritoEntity.getFecha_creacion());
        carritoDto.setFecha_recibida(carritoEntity.getFecha_recibida());
        carritoDto.setTotal_a_pagar(carritoEntity.getTotal_a_pagar());
        carritoDto.setEstado_carrito(carritoEntity.getEstado_carrito());

        // Ahora, también copia los atributos relacionados al usuario
        carritoDto.setId_usuario(carritoEntity.getUsuarioEntity().getId_usuario());
        carritoDto.setUsuario_nombre_apellidos(carritoEntity.getUsuarioEntity().getNombre() + " " + carritoEntity.getUsuarioEntity().getApellido());
        carritoDto.setUsuario_email(carritoEntity.getUsuarioEntity().getEmail());

        // Puedes seguir copiando otros atributos relacionados al usuario y detalles del carrito
        return carritoDto;
    }

// Método para convertir un DatosUsuarioDto a una entidad UsuarioEntity
    private UsuarioEntity convertirDatosUsuarioDtoAUsuarioEntity(DatosUsuarioDto datosUsuarioDto) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        // Copia los atributos del DTO a la entidad
        usuarioEntity.setId_usuario(datosUsuarioDto.getId_usuario());
        // Copia otros atributos según sea necesario, como nombre, apellidos, email, etc.
        return usuarioEntity;
    }

}

/*
    @Override
    public CarritoDto guardarCarrito(CarritoDto carritoDto, Long id_usuario) {
        // Crear una nueva instancia de CarritoEntity y establecer sus atributos
        CarritoEntity carritoEntity = new CarritoEntity();
        carritoEntity.setId_carrito(carritoDto.getId_carrito());
        carritoEntity.setNumero(carritoDto.getNumero());
        carritoEntity.setFecha_creacion(carritoDto.getFecha_creacion());
        carritoEntity.setFecha_recibida(carritoDto.getFecha_recibida());
        carritoEntity.setTotal_a_pagar(carritoDto.getTotal_a_pagar());
        carritoEntity.setEstado_carrito(carritoDto.getEstado_carrito());

        // Obtén el usuario correspondiente a través del servicio de usuarios
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id_usuario).orElse(null);

        // Asocia el usuario al carrito
        carritoEntity.setUsuarioEntity(usuarioEntity);

        // Guarda el carrito en la base de datos
        carritoEntity = carritoRepository.save(carritoEntity);

        // Convertir el CarritoEntity nuevamente en CarritoDto manualmente
        carritoDto.setId_carrito(carritoEntity.getId_carrito());
        carritoDto.setNumero(carritoEntity.getNumero());
        carritoDto.setFecha_creacion(carritoEntity.getFecha_creacion());
        carritoDto.setFecha_recibida(carritoEntity.getFecha_recibida());
        carritoDto.setTotal_a_pagar(carritoEntity.getTotal_a_pagar());
        carritoDto.setEstado_carrito(carritoEntity.getEstado_carrito());

        return carritoDto;

    }
 */
 /*
    @Override
    public CarritoDto obtenerCarritoPorUsuario(String email) {
        // Asumiendo que tienes un método en tu servicio de usuarios para obtener un UsuarioEntity
        DatosUsuarioDto datosUsuarioDto = usuarioService.ObtenerUsuarioPorEmail(email);

        if (datosUsuarioDto == null) {
            return null; // Manejo de usuario no encontrado
        }

        CarritoEntity carritoEntity = carritoRepository.findByUsuarioEntity(usuarioEntity);

        if (carritoEntity == null) {
            return null; // Manejo de carrito no encontrado
        }

        CarritoDto carritoDto = new CarritoDto();
        carritoDto.setId_carrito(carritoEntity.getId_carrito());
        carritoDto.setNumero(carritoEntity.getNumero());
        carritoDto.setFecha_creacion(carritoEntity.getFecha_creacion());
        carritoDto.setFecha_recibida(carritoEntity.getFecha_recibida());
        carritoDto.setTotal_a_pagar(carritoEntity.getTotal_a_pagar());
        carritoDto.setEstado_carrito(carritoEntity.getEstado_carrito());

        // Convierte también los atributos relacionados al usuario y detalles del carrito
        carritoDto.setDatosUsuarioDto(converterUsuarioEntityToDto(usuarioEntity));
        carritoDto.setDetallesCarritoDto(converterDetallesCarritoEntityToDto(carritoEntity.getDetallesCarritoEntity()));

        return carritoDto;
    }
 */
 /*
// Métodos para convertir UsuarioEntity y DetallesCarritoEntity a sus respectivos DTOs
    private DatosUsuarioDto converterUsuarioEntityToDto(UsuarioEntity usuarioEntity) {
        DatosUsuarioDto datosUsuarioDto = new DatosUsuarioDto();
        datosUsuarioDto.setId_usuario(usuarioEntity.getId_usuario());
        datosUsuarioDto.setNombre(usuarioEntity.getNombre());
        // Otras conversiones
        return datosUsuarioDto;
    }

    private List<DetallesCarritoDto> converterDetallesCarritoEntityToDto(List<DetallesCarritoEntity> detallesCarritoEntities) {
        List<DetallesCarritoDto> detallesCarritoDtos = new ArrayList<>();
        for (DetallesCarritoEntity detalleEntity : detallesCarritoEntities) {
            DetallesCarritoDto detalleDto = new DetallesCarritoDto();
            detalleDto.setId_detallescarrito(detalleEntity.getId_detallecarrito());
            detalleDto.setCantidad(detalleEntity.getCantidad());
            detalleDto.setTotal(detalleEntity.getTotal());
            // Otras conversiones
            detallesCarritoDtos.add(detalleDto);
        }
        return detallesCarritoDtos;
    }

    @Override
    public CarritoDto obtenerCarritoConDetallesPorUsuario(DatosUsuarioDto datosUsuarioDto
    ) {
        UsuarioEntity usuarioEntity = usuarioRepository.findUserByEmail(datosUsuarioDto.getEmail());

        if (usuarioEntity != null) {
            // El usuario existe, ahora busca su carrito asociado
            List<CarritoEntity> carritos = carritoRepository.findCarritoWithDetallesByUsuarioEntity(usuarioEntity);

            if (!carritos.isEmpty()) {
                // Si se encontró un carrito, conviértelo en un CarritoDto
                CarritoEntity carritoEntity = carritos.get(0);
                CarritoDto carritoDto = new CarritoDto();
                carritoDto.setId_carrito(carritoEntity.getId_carrito());
                carritoDto.setNumero(carritoEntity.getNumero());
                carritoDto.setFecha_creacion(carritoEntity.getFecha_creacion());
                carritoDto.setFecha_recibida(carritoEntity.getFecha_recibida());
                carritoDto.setTotal_a_pagar(carritoEntity.getTotal_a_pagar());
                carritoDto.setEstado_carrito(carritoEntity.getEstado_carrito());
                // También debes configurar el usuario si lo necesitas
                // carritoDto.setDatosUsuarioDto(convertirUsuarioEntityADatosUsuarioDto(carritoEntity.getUsuarioEntity()));
                // Configura el usuario en el DTO si es necesario
                DatosUsuarioDto datosUsuarioDto = new DatosUsuarioDto();

                if (usuarioEntity != null) {
                    datosUsuarioDto.setId_usuario(usuarioEntity.getId_usuario());
                    datosUsuarioDto.setNombre(usuarioEntity.getNombre());
                    datosUsuarioDto.setApellido(usuarioEntity.getApellido());
                    datosUsuarioDto.setEmail(usuarioEntity.getEmail());
                    // Puedes seguir agregando otros campos según las necesidades

                    // Asegúrate de que estás manejando adecuadamente otros campos si existen más en el usuarioEntity
                }

                carritoDto.setDatosUsuarioDto(datosUsuarioDto);

                return carritoDto;
            }
        }

        // Si no se encontró un carrito, crea uno nuevo con detalles vacíos
        CarritoDto carritoDto = new CarritoDto();
        carritoDto.setFecha_creacion(new Date());
        carritoDto.setDatosUsuarioDto(datosUsuarioDto);
        carritoDto.setEstado_carrito(true);
        carritoDto.setDetallesCarritoDto(new ArrayList<>());

        carritoDto = guardarCarrito(carritoDto); // Guardar el carrito en la base de datos
        logger.info("SE HA OBTENIDO LOS DETALLES DEL CARRITO: " + carritoDto);

        return carritoDto;
    }

    @Override
    public void CalcularTotalCarrito(CarritoDto carritoDto
    ) {
        double totalCarrito = 0.0;
        List<DetallesCarritoDto> detalles = carritoDto.getDetallesCarritoDto();

        for (DetallesCarritoDto detalle : detalles) {
            double precio = detalle.getProductodto().getPreciooferta() != null
                    ? detalle.getProductodto().getPreciooferta()
                    : detalle.getProductodto().getPrecionormal();
            totalCarrito += detalle.getCantidad() * precio;
        }

        carritoDto.setTotal_a_pagar(totalCarrito);
        // Guardar los detalles del carrito en la base de datos si es necesario
        for (DetallesCarritoDto detalle : detalles) {
            detalleCarritoService.guardarDetalleCarrito(detalle, carritoDto);
        }
    }*/
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

    private CarritoDto convertirCarritoEntityACarritoDto(CarritoEntity carritoEntity) {
        CarritoDto carritoDto = new CarritoDto();
        carritoDto.setId_carrito(carritoEntity.getId_carrito());
        carritoDto.setNumero(carritoEntity.getNumero());
        carritoDto.setFecha_creacion(carritoEntity.getFecha_creacion());
        carritoDto.setFecha_recibida(carritoEntity.getFecha_recibida());
        carritoDto.setTotal_a_pagar(carritoEntity.getTotal_a_pagar());
        carritoDto.setEstado_carrito(carritoEntity.getEstado_carrito());
        // También debes configurar el usuario si lo necesitas
        // carritoDto.setDatosUsuarioDto(convertirUsuarioEntityADatosUsuarioDto(carritoEntity.getUsuarioEntity()));
        // Configura el usuario en el DTO si es necesario
        DatosUsuarioDto datosUsuarioDto = convertirUsuarioEntityADatosUsuarioDto(carritoEntity.getUsuarioEntity());
        carritoDto.setDatosUsuarioDto(datosUsuarioDto);

        return carritoDto;

    }

    private DatosUsuarioDto convertirUsuarioEntityADatosUsuarioDto(UsuarioEntity usuarioEntity) {
        DatosUsuarioDto datosUsuarioDto = new DatosUsuarioDto();

        if (usuarioEntity != null) {
            datosUsuarioDto.setId_usuario(usuarioEntity.getId_usuario());
            datosUsuarioDto.setNombre(usuarioEntity.getNombre());
            datosUsuarioDto.setApellido(usuarioEntity.getApellido());
            datosUsuarioDto.setEmail(usuarioEntity.getEmail());
            // Puedes seguir agregando otros campos según las necesidades

            // Asegúrate de que estás manejando adecuadamente otros campos si existen más en el usuarioEntity
        }

        return datosUsuarioDto;
    }
 */
// Métodos para convertir UsuarioEntity y DetallesCarritoEntity a sus respectivos DTOs
/*
private DatosUsuarioDto converterUsuarioEntityToDto(UsuarioEntity usuarioEntity) {
    DatosUsuarioDto datosUsuarioDto = new DatosUsuarioDto();
    datosUsuarioDto.setId_usuario(usuarioEntity.getId_usuario());
    datosUsuarioDto.setNombre(usuarioEntity.getNombre());
    // Otras conversiones
    return datosUsuarioDto;
}

private List<DetallesCarritoDto> converterDetallesCarritoEntityToDto(List<DetallesCarritoEntity> detallesCarritoEntities) {
    List<DetallesCarritoDto> detallesCarritoDtos = new ArrayList<>();
    for (DetallesCarritoEntity detalleEntity : detallesCarritoEntities) {
        DetallesCarritoDto detalleDto = new DetallesCarritoDto();
        detalleDto.setId_detallescarrito(detalleEntity.getId_detallecarrito());
        detalleDto.setCantidad(detalleEntity.getCantidad());
        detalleDto.setTotal(detalleEntity.getTotal());
        // Otras conversiones
        detallesCarritoDtos.add(detalleDto);
    }
    return detallesCarritoDtos;
}
 */
