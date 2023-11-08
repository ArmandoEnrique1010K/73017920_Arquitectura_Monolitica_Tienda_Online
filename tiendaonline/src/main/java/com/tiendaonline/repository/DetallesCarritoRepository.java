package com.tiendaonline.repository;

import com.tiendaonline.entity.DetallesCarritoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesCarritoRepository extends JpaRepository<DetallesCarritoEntity, Long>{
    // BUSCAR DETALLES DEL CARRITO POR EL ID DEL CARRITO
    // Utilizando @Query para buscar detalles del carrito por el ID del carrito
    @Query("SELECT dce FROM DetallesCarritoEntity dce WHERE dce.carritoEntity.id_carrito = :id_carrito")
    List<DetallesCarritoEntity> findDetallesByCarritoId_carrito(Long id_carrito);

}
