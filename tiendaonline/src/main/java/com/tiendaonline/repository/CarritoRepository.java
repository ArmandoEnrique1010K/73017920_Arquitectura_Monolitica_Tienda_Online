package com.tiendaonline.repository;

import com.tiendaonline.dto.DatosUsuarioDto;
import com.tiendaonline.entity.CarritoEntity;
import com.tiendaonline.entity.UsuarioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoEntity, Long>{
    
    @Query("SELECT c FROM CarritoEntity c WHERE c.usuarioEntity = :usuarioEntity AND c.estado_carrito = true")
    List<CarritoEntity> findByUsuarioEntityAndEstadoCarritoTrue (UsuarioEntity usuarioEntity);

    // List<CarritoEntity> findActiveCarritosForUser(UsuarioEntity usuarioEntity);
    List<CarritoEntity> findCarritoWithDetallesByUsuarioEntity (UsuarioEntity usuarioEntity);

    public List<CarritoEntity> findByUsuarioEntity(DatosUsuarioDto datosUsuarioDto);

}
